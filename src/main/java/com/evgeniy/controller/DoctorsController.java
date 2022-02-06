package com.evgeniy.controller;

import com.evgeniy.entity.*;
import com.evgeniy.repository.*;
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
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Controller
public class DoctorsController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientCardService patientCardService;
    @Autowired
    private TreatmentInformationService treatmentInformationService;
    @Autowired
    private AppointmentService appointmentService;

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


    @GetMapping("/doctor")
    public String doctor1(Model model) {
        Iterable<Patient> patient = patientService.findAll();
        model.addAttribute("patient", patient);
        return "doctor";
    }

    @PostMapping("/doctor")
    public String postDoctor1(@RequestParam(value = "id") Long id,
                              Model model) {
        Iterable<Patient> patient = patientService.findAll();
        model.addAttribute("patient", patient);

        return "doctor";
    }


    @GetMapping("/doctor/patientmenu")
    public String getPatientMenu(Model model) {
        Iterable<Patient> patient = patientService.findAll();
        model.addAttribute("patient", patient);

        return "doctor/patientmenu";
    }

    @PostMapping("/doctor/patientmenu")
    public String postPatientmenu(@RequestParam(value = "id") Long id,
                                  @RequestParam(value = "diagnosis") String diagnosis,
                                  @RequestParam(value = "recommendations") String recommendations,
                                  @RequestParam(value = "symptoms") String symptoms,
                                  @RequestParam(value = "treatment") String treatment,
                                  Model model) {

        treatmentInformationService.CreateTreatmentInformation(id, diagnosis, recommendations, symptoms, treatment);

        return "doctor/patientmenu";
    }

    @PostMapping("/doctor/patient")
    public String postPatient(Model model) {

        return "doctor/patient";
    }

    @GetMapping("/doctor/patient")
    public String getPatient(@RequestParam(value = "id") Long id,
                             Model model) {
        Patient patient = patientService.findPatientById(id);
        model.addAttribute("patient", patient);
        return "doctor/patient";
    }

    @PostMapping("/doctor/patientAppointment")
    public String postPatientAppointment(Model model) {

        return "doctor/patientAppointment";
    }

    @GetMapping("/doctor/patientAppointment")
    public String getPatientAppointment(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Iterable<AppointmentToDoctors> appointmentToDoctors = appointmentService.findAllByDoctor_Id(((User) auth.getPrincipal()).getDoctor().getId());
        model.addAttribute("appointmentToDoctors", appointmentToDoctors);


        return "doctor/patientAppointment";
    }


}