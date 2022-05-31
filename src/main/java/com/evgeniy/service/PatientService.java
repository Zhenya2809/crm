package com.evgeniy.service;

import com.evgeniy.entity.Patient;
import com.evgeniy.entity.PatientCard;
import com.evgeniy.repository.PatientRepository;
import com.evgeniy.telegram.ExecutionContext;
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
    private PatientCardService patientCardService;

    public void createPatient(String fio, String birthday, String sex, String placeOfResidence, String insurancePolicy, String phoneNumber) {
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

    public void save(Patient patient) {
        patientRepository.save(patient);
    }

    public Patient findPatientByEmail(String email, ExecutionContext executionContext) {
        Optional<Patient> byEmail = patientRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            return byEmail.get();
        }
        Patient patient=new Patient();
        patient.setChatId(executionContext.getChatId());
        patient.setEmail(email);
        patient.setFio(executionContext.getFirstName() + " " + executionContext.getLastName());
        patientRepository.save(patient);
        return patient;

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

    public Optional<Patient> findPatientByAuthEmail() {
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

    public Optional<PatientCard> findPatientCardByPatient(Patient patient) {
        return patientCardService.findPatientCardByPatient(patient);
    }

}
