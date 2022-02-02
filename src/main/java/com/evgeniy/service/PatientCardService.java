package com.evgeniy.service;

import com.evgeniy.entity.Patient;
import com.evgeniy.repository.PatientInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientCardService {
    @Autowired
    PatientInfoRepository patientInfoRepository;

    public Patient findPatientCard(Long id) {
        Patient patient = new Patient();
        Optional<Patient> byId = patientInfoRepository.findById(id);
        byId.ifPresent(e -> {
            Long idd = e.getId();
            String email = e.getEmail();
            String fio = e.getFio();
            String sex = e.getSex();
            String birthday = e.getBirthday();
            String placeOfResidence = e.getPlaceOfResidence();
            String insurancePolicy = e.getInsurancePolicy();
            String diagnosis = e.getDiagnosis();
            String startDate = e.getStartDate();
            String finishDate = e.getFinishDate();
            patient.setId(idd);
            patient.setEmail(email);
            patient.setBirthday(birthday);
            patient.setFio(fio);
            patient.setSex(sex);
            patient.setPlaceOfResidence(placeOfResidence);
            patient.setInsurancePolicy(insurancePolicy);
            patient.setDiagnosis(diagnosis);
            patient.setStartDate(startDate);
            patient.setFinishDate(finishDate);
        });
        return patient;
    }
}
