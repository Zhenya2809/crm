package com.evgeniy.service;

import com.evgeniy.entity.Patient;
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

    public void CreatePatient(String fio,String birthday,String sex,String placeOfResidence, String insurancePolicy){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Patient patient = new Patient();
        patient.setFio(fio);
        patient.setBirthday(birthday);
        patient.setSex(sex);
        patient.setPlaceOfResidence(placeOfResidence);
        patient.setInsurancePolicy(insurancePolicy);
        patient.setEmail(auth.getName());
        patientRepository.save(patient);
    }
    public Iterable<Patient> findAllByEmail(String email){
       return patientRepository.findAllByEmail(email);
    }
    public Iterable<Patient> findAll(){
        return patientRepository.findAll();
    }
    public Optional<Patient> findById(Long id){
         return patientRepository.findById(id);
    }
    public Patient findPatientById(Long id){
        return patientRepository.findPatientById(id);
    }
}
