package com.evgeniy.service;

import com.evgeniy.entity.PatientCard;
import com.evgeniy.repository.PatientCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientCardService {
    @Autowired
    private PatientCardRepository patientCardRepository;
    public PatientCard findPatientCardById(Long id){
        return patientCardRepository.findPatientCardById(id);
    }
}
