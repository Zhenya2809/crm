package com.evgeniy.service;

import com.evgeniy.entity.Doctor;
import com.evgeniy.entity.Patient;
import com.evgeniy.entity.PatientCard;
import com.evgeniy.entity.TreatmentInformation;
import com.evgeniy.repository.TreatmentInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class TreatmentInformationService {
    @Autowired
    private TreatmentInformationRepository treatmentInformationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientCardService patientCardService;

    public void editTreatmentInformation(Long id, String diagnosis, String recommendations, String symptoms, String treatment) {
        Date todayDate = new Date();
        SimpleDateFormat formatForDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatForTime = new SimpleDateFormat("HH.mm");

        Doctor doctor = userService.findDoctorByLogin();
        Patient patient = patientService.findPatientById(id);


        Optional<PatientCard> patientCardByPatient = patientCardService.findPatientCardByPatient(patient);
        if (patientCardByPatient.isPresent()) {

            TreatmentInformation treatmentInformation = new TreatmentInformation();
            treatmentInformation.setDoctor(doctor);
            treatmentInformation.setDiagnosis(diagnosis);
            treatmentInformation.setDate(formatForDate.format(todayDate));
            treatmentInformation.setTime(formatForTime.format(todayDate));
            treatmentInformation.setPatientCard(patientCardByPatient.get());
            treatmentInformation.setRecommendations(recommendations);
            treatmentInformation.setSymptoms(symptoms);
            treatmentInformation.setTreatment(treatment);
            treatmentInformationRepository.save(treatmentInformation);
        }
    }
}
