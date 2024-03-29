package com.evgeniy.controller;

import com.evgeniy.entity.*;
import com.evgeniy.repository.*;
import com.evgeniy.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


@Controller
@Slf4j
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


    @PostMapping("/profileEdit")

    public String postProfileEdit(@RequestParam(value = "fio") String fio,
                                  @RequestParam(value = "sex") String sex,
                                  @RequestParam(value = "birthday") String birthday,
                                  @RequestParam(value = "placeOfResidence") String placeOfResidence,
                                  @RequestParam(value = "insurancePolicy") String insurancePolicy,
                                  @RequestParam(value = "phoneNumber") String phoneNumber,
                                  Model model) {

        patientService.createPatient(fio, birthday, sex, placeOfResidence, insurancePolicy, phoneNumber);
        return "patient/profileEdit";
    }

    @GetMapping("/profileEdit")
    public String getProfileEdit(Model model) {
        Optional<Patient> patient = patientService.findPatientByAuthEmail();
        patient.ifPresent(e -> model.addAttribute("patient", patient.get()));
        return "patient/profileEdit";
    }

    @GetMapping("/profile")
    public String getMyProfile(Model model) {
        Optional<Patient> patient = patientService.findPatientByAuthEmail();
        patient.ifPresent(e -> model.addAttribute("patient", patient.get()));

        return "patient/myProfile";
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

            Optional<Patient> patientOptional = patientService.findPatientByFioContains(name);
            if (patientOptional.isPresent()) {
                Patient patient = patientOptional.get();
                List<AppointmentToDoctors> appointmentToDoctors = patient.getAppointmentToDoctors();
                model.addAttribute("appointments", appointmentToDoctors);
                model.addAttribute("patient", patient);
            }
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

        treatmentInformationService.editTreatmentInformation(id, diagnosis, recommendations, symptoms, treatment);

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

    @GetMapping("/patient/{id}/edit")
    public String getPatientInfoEdit(@PathVariable(value = "id") long id,
                                     Model mode) {
        return "patient/changePatientProfile";

    }

    @PostMapping("/patient/{id}/edit")
    public String postPatientInfoEdit(@PathVariable(value = "id") long id,
                                      @RequestParam(value = "fio") String fio,
                                      @RequestParam(value = "sex") String sex,
                                      @RequestParam(value = "birthday") String birthday,
                                      @RequestParam(value = "placeOfResidence") String placeOfResidence,
                                      @RequestParam(value = "insurancePolicy") String insurancePolicy,
                                      @RequestParam(value = "phoneNumber") String phoneNumber,
                                      Model mode) {
        patientService.editPatient(id, birthday, insurancePolicy, placeOfResidence, sex, fio, phoneNumber);
        return "redirect:/profile";

    }

    @PostMapping("/doctor/patientAppointment")
    public String postPatientAppointment(Model model) {

        return "doctor/patientAppointment";
    }

    @GetMapping("/doctor/patientAppointment")
    public String getPatientAppointment(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("получив юзера--->>" + auth.getName() + "<<--- 100% \"doctor1\" я не еблан...");
        Iterable<AppointmentToDoctors> appointmentToDoctors = appointmentService.findAllByDoctor_Id(((User) auth.getPrincipal()).getDoctor().getId());
        System.out.println("получив айди доктора--->>" + ((User) auth.getPrincipal()).getDoctor().getId() + "<<--- 100% тут \"id1\"");

        model.addAttribute("appointmentToDoctors", appointmentToDoctors);
        System.out.println("инфа ушла на страничку");
        return "doctor/patientAppointment";
    }

    @PostMapping("/doctor/patientAppointment/{id}/edit")
    public String postAppointmentPatientEdit(@PathVariable(value = "id") long id,
                                             @RequestParam String diagnosis,
                                             @RequestParam String recommendations,
                                             @RequestParam String symptoms,
                                             @RequestParam String treatment,
                                             Model mode) {


        treatmentInformationService.editTreatmentInformation(id, diagnosis, recommendations, symptoms, treatment);

        return "redirect:/doctor/patientAppointment";

    }

    @GetMapping("/doctor/patientAppointment/{id}/delete")
    public String getAppointmentPatientDelete(@PathVariable(value = "id") long id,
                                              Model mode) {

        appointmentService.deleteAppointmentByDoctorId(id);
        return "redirect:/doctor/patientAppointment";

    }

    @GetMapping("/doctor/patientAppointment/{id}/edit")
    public String getAppointmentPatientEdit(@PathVariable(value = "id") long id,
                                            Model mode) {
        return "doctor/appointmentEdit";

    }

    @GetMapping("/doctor/patientTreatment/{id}/check")
    public String getPatientTreatmentCheck(@PathVariable(value = "id") long id,
                                           Model model) {
        Optional<Patient> patientOptional = patientService.findById(id);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();

            Optional<PatientCard> patientCardOptional = patientService.findPatientCardByPatient(patient);
            if (patientCardOptional.isPresent()) {
                PatientCard patientCard = patientCardOptional.get();
                Set<TreatmentInformation> treatmentInformation = patientCard.getTreatmentInformation();
                model.addAttribute("treatmentInformation", treatmentInformation);
            }
        }
        return "doctor/patientTreatment";

    }
}