package com.evgeniy.service;

import com.evgeniy.entity.Patient;
import com.evgeniy.entity.PatientCard;
import com.evgeniy.repository.PatientCardRepository;
import com.evgeniy.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientCardRepository patientCardRepository;

    public void CreatePatient(String fio, String birthday, String sex, String placeOfResidence, String insurancePolicy, String phoneNumber) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Patient patient = new Patient();
        patient.setFio(fio);
        patient.setBirthday(birthday);
        patient.setSex(sex);
        patient.setPlaceOfResidence(placeOfResidence);
        patient.setInsurancePolicy(insurancePolicy);
        patient.setEmail(auth.getName());
        patient.setPhoneNumber(phoneNumber);
        patientRepository.save(patient);
    }

    public Iterable<Patient> findAllByEmail(String email) {
        return patientRepository.findAllByEmail(email);
    }

    public Iterable<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient findPatientById(Long id) {
        return patientRepository.findPatientById(id);
    }

    public Optional<Patient> findPatientByFio(String name) {
        return patientRepository.findPatientByFio(name);
    }

    public Optional<Patient> findPatientByFioContains(String fio) {
        return patientRepository.findPatientByFioContains(fio);
    }

    public Optional<Patient> findPatienByAuthEmail() {
        return patientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }



    public void editPatient(Long id, String birthday, String insurancePolicy, String placeOfResidence, String sex, String fio, String phoneNumber) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Patient patient = patientRepository.findPatientById(id);

        patient.setBirthday(birthday);
        patient.setEmail(auth.getName());
        patient.setInsurancePolicy(insurancePolicy);
        patient.setPlaceOfResidence(placeOfResidence);
        patient.setSex(sex);
        patient.setFio(fio);
        patient.setPhoneNumber(phoneNumber);
        patientRepository.save(patient);

    }
    public PatientCard findPatientCardByPatientId(Long id) {
        return patientCardRepository.findPatientCardByPatientId(id);
    }

    public Optional<PatientCard> findPatientCardByPatient(Patient patient) {
        return patientCardRepository.findPatientCardByPatient(patient);
    }

}
