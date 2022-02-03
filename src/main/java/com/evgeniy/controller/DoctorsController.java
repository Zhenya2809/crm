package com.evgeniy.controller;

import com.evgeniy.entity.Doctor;
import com.evgeniy.entity.Patient;
import com.evgeniy.entity.PatientCard;
import com.evgeniy.entity.User;
import com.evgeniy.repository.DoctorRepository;
import com.evgeniy.repository.PatientCardRepository;
import com.evgeniy.repository.PatientRepository;
import com.evgeniy.repository.UserRepository;
import com.evgeniy.service.AppointmentService;
import com.evgeniy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientCardRepository patientCardRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private UserRepository userRepository;

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
        patient.setBirthday(birthday);
        patient.setSex(sex);
        patient.setPlaceOfResidence(placeOfResidence);
        patient.setInsurancePolicy(insurancePolicy);
        patient.setEmail(auth.getName());
        patientRepository.save(patient);

        return "profile";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Iterable<Patient> patientCards = patientRepository.findAllByEmail(auth.getName());
        model.addAttribute("patientCard", patientCards);
        return "profile";
    }


    @GetMapping("/doctor1")
    public String doctor1(Model model) {
        Iterable<Patient> patient = patientRepository.findAll();
        model.addAttribute("patient", patient);
        return "doctor1";
    }

    @PostMapping("/doctor1")
    public String postDoctor1(@RequestParam(value = "id") Long id,
                              Model model) {
        patientRepository.findById(id);

        return "doctor1";
    }


    @GetMapping("/doctor1/patientmenu")
    public String getPatientMenu(Model model) {
        Iterable<Patient> patientCard = patientRepository.findAll();
        model.addAttribute("patientCard", patientCard);

        return "doctor1/patientmenu";
    }

    @PostMapping("/doctor1/patientmenu")
    public String postPatientmenu(@RequestParam(value = "id") Long id,
                                  @RequestParam(value = "diagnosis") String diagnosis,
                                  Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Doctor doctor = userRepository.findByUsername(auth.getName()).getDoctor();
        Patient patient = patientRepository.findPatientById(id);

        PatientCard patientCard = new PatientCard();
        patientCard.setPatient(patient);
        patientCard.setDiagnosis(diagnosis);
        patientCard.setDoctor(doctor);


        patientCardRepository.save(patientCard);


        return "doctor1/patientmenu";
    }

    @PostMapping("/doctor1/patient")
    public String postPatient(@RequestParam(value = "id") Long id,
                              Model model) {
        PatientCard patientCard = patientCardRepository.findPatientCardById(id);
        model.addAttribute("patientCard", patientCard);
        return "doctor1/patient";
    }
}