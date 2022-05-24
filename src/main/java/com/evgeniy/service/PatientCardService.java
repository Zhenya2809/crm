package com.evgeniy.service;

import com.evgeniy.entity.Patient;
import com.evgeniy.entity.PatientCard;
import com.evgeniy.repository.PatientCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientCardService {
    @Autowired
    private PatientCardRepository patientCardRepository;

    public PatientCard findPatientCardById(Long id) {
        return patientCardRepository.findPatientCardById(id);
    }

    public void save(PatientCard patientCard) {
        patientCardRepository.save(patientCard);
    }

    public PatientCard findPatientCardByPatientId(Long id) {
        return patientCardRepository.findPatientCardByPatientId(id);
    }

    public Optional<PatientCard> findPatientCardByPatient(Patient patient) {
        return patientCardRepository.findPatientCardByPatient(patient);
    }
}
