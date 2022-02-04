package com.evgeniy.service;

import com.evgeniy.entity.Doctor;
import com.evgeniy.entity.Patient;
import com.evgeniy.entity.PatientCard;
import com.evgeniy.entity.TreatmentInformation;
import com.evgeniy.repository.PatientCardRepository;
import com.evgeniy.repository.PatientRepository;
import com.evgeniy.repository.TreatmentInformationRepository;
import com.evgeniy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TreatmentInformationService {
    @Autowired
    private TreatmentInformationRepository treatmentInformationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientCardRepository patientCardRepository;

    public void CreateTreatmentInformation(Long id, String diagnosis, String recommendations, String symptoms, String treatment) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Date todayDate = new Date();
        SimpleDateFormat formatForDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatForTime = new SimpleDateFormat("HH-mm");

        Doctor doctor = userRepository.findByUsername(auth.getName()).getDoctor();
        Patient patient = patientRepository.findPatientById(id);
        PatientCard patientCard = patientCardRepository.findPatientCardByPatient(patient);

        TreatmentInformation treatmentInformation = new TreatmentInformation();
        treatmentInformation.setDoctor(doctor);
        treatmentInformation.setDiagnosis(diagnosis);
        treatmentInformation.setDate(formatForDate.format(todayDate));
        treatmentInformation.setTime(formatForTime.format(todayDate));
        treatmentInformation.setPatientCard(patientCard);
        treatmentInformation.setRecommendations(recommendations);
        treatmentInformation.setSymptoms(symptoms);
        treatmentInformation.setTreatment(treatment);
        treatmentInformation.setPatientCard(patientCard);
    }
}
