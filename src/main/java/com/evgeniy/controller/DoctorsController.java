package com.evgeniy.controller;

import com.evgeniy.entity.*;
import com.evgeniy.repository.DoctorRepository;
import com.evgeniy.repository.PatientCardRepository;
import com.evgeniy.repository.PatientRepository;
import com.evgeniy.repository.UserRepository;
import com.evgeniy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


@Controller
public class DoctorsController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientCardService patientCardService;
    @Autowired
    private TreatmentInformationService treatmentInformationService;

    @PostMapping("/profile")
    public String postProfile(@RequestParam(value = "fio") String fio,
                              @RequestParam(value = "sex") String sex,
                              @RequestParam(value = "birthday") String birthday,
                              @RequestParam(value = "placeOfResidence") String placeOfResidence,
                              @RequestParam(value = "insurancePolicy") String insurancePolicy,
                              Model model) {

        patientService.CreatePatient(fio, birthday, sex, placeOfResidence, insurancePolicy);
        return "profile";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Iterable<Patient> patientCards = patientService.findAllByEmail(auth.getName());
        model.addAttribute("patientCard", patientCards);
        return "profile";
    }


    @GetMapping("/doctor1")
    public String doctor1(Model model) {
        Iterable<Patient> patient = patientService.findAll();
        model.addAttribute("patient", patient);
        return "doctor1";
    }

    @PostMapping("/doctor1")
    public String postDoctor1(@RequestParam(value = "id") Long id,
                              Model model) {
        patientService.findById(id);

        return "doctor1";
    }


    @GetMapping("/doctor1/patientmenu")
    public String getPatientMenu(Model model) {
        Iterable<Patient> patientCard = patientService.findAll();
        model.addAttribute("patientCard", patientCard);

        return "doctor1/patientmenu";
    }

    @PostMapping("/doctor1/patientmenu")
    public String postPatientmenu(@RequestParam(value = "id") Long id,
                                  @RequestParam(value = "diagnosis") String diagnosis,
                                  @RequestParam(value = "recommendations") String recommendations,
                                  @RequestParam(value = "symptoms") String symptoms,
                                  @RequestParam(value = "treatment") String treatment,
                                  Model model) {

        treatmentInformationService.CreateTreatmentInformation(id, diagnosis, recommendations, symptoms, treatment);

        return "doctor1/patientmenu";
    }

    @PostMapping("/doctor1/patient")
    public String postPatient(@RequestParam(value = "id") Long id,
                              Model model) {
        PatientCard patientCard = patientCardService.findPatientCardById(id);
        model.addAttribute("patientCard", patientCard);
        return "doctor1/patient";
    }
}