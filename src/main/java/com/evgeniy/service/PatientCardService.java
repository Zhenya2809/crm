package com.evgeniy.service;

import com.evgeniy.entity.PatientCard;
import com.evgeniy.repository.PatientCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientCardService {
    @Autowired
    PatientCardRepository patientCardRepository;

    public PatientCard findPatientCard(Long id) {
        PatientCard patientCard = new PatientCard();
        Optional<PatientCard> byId = patientCardRepository.findById(id);
        byId.stream().forEach(e -> {

            String email = e.getEmail();
            String fio = e.getFio();
            String sex = e.getSex();
            String birthday = e.getBirthday();
            String placeOfResidence= e.getPlaceOfResidence();
            String insurancePolicy = e.getInsurancePolicy();
            String diagnosis = e.getDiagnosis();
            String startDate = e.getStartDate();
            String finishDate= e.getFinishDate();
            patientCard.setEmail(email);
            patientCard.setBirthday(birthday);
            patientCard.setFio(fio);
            patientCard.setSex(sex);
            patientCard.setPlaceOfResidence(placeOfResidence);
            patientCard.setInsurancePolicy(insurancePolicy);
            patientCard.setDiagnosis(diagnosis);
            patientCard.setStartDate(startDate);
            patientCard.setFinishDate(finishDate);
        });
        return patientCard;
    }
}
