package com.evgeniy.controller;

import com.evgeniy.entity.Patient;
import com.evgeniy.repository.PatientInfoRepository;
import com.evgeniy.service.AppointmentService;
import com.evgeniy.service.PatientCardService;
import com.evgeniy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class DoctorsController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private UserService userService;
    @Autowired
    PatientCardService patientCardService;
    @Autowired
    private PatientInfoRepository patientInfoRepository;


    @PostMapping("/profile")
    public String postProfile(@RequestParam(value = "fio") String fio,
                              @RequestParam(value = "sex") String sex,
                              @RequestParam(value = "birthday") String birthday,
                              @RequestParam(value = "placeOfResidence") String placeOfResidence,
                              @RequestParam(value = "insurancePolicy") String insurancePolicy,
                              Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Patient patient = new Patient();
        patient.setFio(fio);
        patient.setSex(sex);
        patient.setBirthday(birthday);
        patient.setPlaceOfResidence(placeOfResidence);
        patient.setInsurancePolicy(insurancePolicy);
        patient.setEmail(auth.getName());
        patientInfoRepository.save(patient);

        return "profile";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Iterable<Patient> patientCards = patientInfoRepository.findAll().stream().filter(card -> card.getEmail().equals(auth.getName())).toList();

        model.addAttribute("patientCard", patientCards);
        return "profile";
    }


    @GetMapping("/doctor1")
    public String doctor1(Model model) {
        Iterable<Patient> patientCard = patientInfoRepository.findAll();
        model.addAttribute("patientCard", patientCard);

        return "doctor1";
    }

    @PostMapping("/doctor1")
    public String postDoctor1(@RequestParam(value = "id") Long id,
                              Model model) {
        patientInfoRepository.findById(id);

        return "doctor1";
    }


    @GetMapping("/doctor1/patientmenu")
    public String getPatientMenu(Model model) {
        Iterable<Patient> patientCard = patientInfoRepository.findAll();
        model.addAttribute("patientCard", patientCard);

        return "doctor1/patientmenu";
    }

    @PostMapping("/doctor1/patientmenu")
    public String postPatientmenu(@RequestParam(value = "id") Long id,
                                  @RequestParam(value = "startDate") String startDate,
                                  @RequestParam(value = "finishDate") String finishDate,
                                  @RequestParam(value = "diagnosis") String diagnosis,
                                  Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Patient patient = patientCardService.findPatientCard(id);

        Patient patient1 = new Patient();

//        patientInfo1.setDoctor(auth.getName());
//        patientInfo1.setDiagnosis(diagnosis);
//        patientInfo1.setStartDate(startDate);
//        patientInfo1.setFinishDate(finishDate);



        patientInfoRepository.save(patient1);
        return "doctor1/patientmenu";
    }

    @PostMapping("/doctor1/patient")
    public String postPatient(@RequestParam(value = "id") Long id,
                              Model model) {
        Patient patient = patientCardService.findPatientCard(id);
        model.addAttribute("patientCard", patient);
        return "doctor1/patient";
    }
}