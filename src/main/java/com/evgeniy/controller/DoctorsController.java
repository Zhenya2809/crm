package com.evgeniy.controller;

import com.evgeniy.entity.PatientCard;
import com.evgeniy.repository.PatientCardRepository;
import com.evgeniy.service.AppointmentService;
import com.evgeniy.service.PatientCardService;
import com.evgeniy.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class DoctorsController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private UserService userService;
    @Autowired
    PatientCardService patientCardService;
    @Autowired
    private PatientCardRepository patientCardRepository;


    @PostMapping("/profile")
    public String postProfile(@RequestParam(value = "fio") String fio,
                              @RequestParam(value = "sex") String sex,
                              @RequestParam(value = "birthday") String birthday,
                              @RequestParam(value = "placeOfResidence") String placeOfResidence,
                              @RequestParam(value = "insurancePolicy") String insurancePolicy,
                              Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PatientCard patient = new PatientCard();
        patient.setFio(fio);
        patient.setSex(sex);
        patient.setBirthday(birthday);
        patient.setPlaceOfResidence(placeOfResidence);
        patient.setInsurancePolicy(insurancePolicy);
        patient.setEmail(auth.getName());
        patientCardRepository.save(patient);

        return "profile";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Iterable<PatientCard> patientCards = patientCardRepository.findAll().stream().filter(card -> card.getEmail().equals(auth.getName())).toList();

        model.addAttribute("patientCard", patientCards);
        return "profile";
    }


    @GetMapping("/doctor1")
    public String doctor1(Model model) {
        Iterable<PatientCard> patientCard = patientCardRepository.findAll();
        model.addAttribute("patientCard", patientCard);

        return "doctor1";
    }

    @PostMapping("/doctor1")
    public String postDoctor1(@RequestParam(value = "id") Long id,
                              Model model) {
        patientCardRepository.findById(id);

        return "doctor1";
    }


    @GetMapping("/doctor1/patientmenu")
    public String getPatientMenu(Model model) {
        Iterable<PatientCard> patientCard = patientCardRepository.findAll();
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
        PatientCard patientCard = patientCardService.findPatientCard(id);

        PatientCard patientCard1 = new PatientCard();

        patientCard1.setDoctor(auth.getName());
        patientCard1.setDiagnosis(diagnosis);
        patientCard1.setStartDate(startDate);
        patientCard1.setFinishDate(finishDate);



        patientCardRepository.save(patientCard1);
        return "doctor1/patientmenu";
    }

    @PostMapping("/doctor1/patient")
    public String postPatient(@RequestParam(value = "id") Long id,
                              Model model) {
        PatientCard patientCard = patientCardService.findPatientCard(id);
        model.addAttribute("patientCard", patientCard);
        return "doctor1/patient";
    }
}