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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;


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
    @Autowired
    private TreatmentInformationRepository treatmentInformationRepository;


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
    public String getDoctor(Model model) {
        return "doctor/doctor";
    }

    @PostMapping("/doctor")
    public String postDoctor(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "name", required = false) String name,
                             Model model) {


        if (id != null) {
            Optional<Patient> patientOptional = patientService.findById(id);
            if (patientOptional.isPresent()) {
                Patient patient = patientOptional.get();
                List<AppointmentToDoctors> appointmentToDoctors = patient.getAppointmentToDoctors();
                model.addAttribute("appointments", appointmentToDoctors);

                model.addAttribute("patient", patient);
            }
        }
        if (name != null) {

            Patient patientByFioContains = patientService.findPatientByFioContains(name);
            model.addAttribute("patient", patientByFioContains);
        }
        return "doctor/doctor";

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
        appointmentToDoctors.forEach(e -> e.getPatient().getId());
        model.addAttribute("appointmentToDoctors", appointmentToDoctors);
        return "doctor/patientAppointment";
    }

    @PostMapping("/doctor/patientAppointment/{id}/edit")
    public String postAppointmentPatientEdit(@PathVariable(value = "id") long id,
                                             @RequestParam String diagnosis,
                                             @RequestParam String recommendations,
                                             @RequestParam String symptoms,
                                             @RequestParam String treatment,
                                             Model mode) {


        treatmentInformationService.CreateTreatmentInformation(id, diagnosis, recommendations, symptoms, treatment);

        return "redirect:/doctor/patientAppointment";

    }

    @GetMapping("/doctor/patientAppointment/{id}/delete")
    public String getAppointmentPatientDelete(@PathVariable(value = "id") long id,
                                              Model mode) {

        appointmentService.deleteAppointment(id);
        return "redirect:/doctor/patientAppointment";

    }

    @GetMapping("/doctor/patientAppointment/{id}/edit")
    public String getAppointmentPatientEdit(@PathVariable(value = "id") long id,
                                            Model mode) {
        return "doctor/appointmentEdit";

    }
}