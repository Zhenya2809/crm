package com.evgeniy.controller;

import com.evgeniy.Application;
import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.entity.Patient;
import com.evgeniy.entity.PatientCard;
import com.evgeniy.entity.TreatmentInformation;
import com.evgeniy.service.AppointmentService;
import com.evgeniy.service.DoctorService;
import com.evgeniy.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@Slf4j
public class MainController {


    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/")
    public String getHome(Model model) {


//ForUsers
        Optional<Patient> patientOptional = patientService.findPatienByAuthEmail();
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            List<AppointmentToDoctors> userAppointmentToDoctor = appointmentService.findAllByPatientId(patient.getId());
            model.addAttribute("userAppointmentToDoctor", userAppointmentToDoctor);
//ForAdmins
            Iterable<AppointmentToDoctors> infoAppointmentToDoctor = appointmentService.findAll();
            model.addAttribute("date", infoAppointmentToDoctor);

            log.info("--> Home page <--");
//ForDoctors
        }


        return "home";
    }

    @PostMapping("/")
    public String postHome(Model model) {
        return "home";
    }

    @GetMapping("/function")
    public String function(Model model) {
        return "function";
    }

    @GetMapping("/clinic")
    public String clinic(Model model) {
        return "clinic";
    }

    //POST
    @PostMapping("/clinic")
    public String postClinic(@RequestParam(value = "date") String date,
                             @RequestParam(value = "time") String time,
                             @RequestParam(value = "doctorID") String doctorID,
                             Model model) {


        if (date.equals("")) {
            return "error/time-reserved";
        }
        return appointmentService.createAppointmentToDoctors(date, time, doctorID);
    }

    @GetMapping("/user/appointment/{id}/delete")
    public String getAppointmentDelete(@PathVariable(value = "id") long id,
                                       Model mode) {
        log.info("--> /user/appointment/{id}/delete page <--");
        appointmentService.deleteAppointmentByDoctorId(id);
        log.info("--> deleted appointment with id=" + id + " <--");
        return "redirect:/";

    }

    @GetMapping("/user/appointment/{id}/edit")
    public String getAppointmentEdit(@PathVariable(value = "id") long id,
                                     Model mode) {
        log.info("--> /user/appointment/{id}/edit page<--");
        return "patient/appointmentEdit";

    }

    @PostMapping("/user/appointment/{id}/edit")
    public String postAppointmentPatientEdit(@PathVariable(value = "id") long id,
                                             @RequestParam(value = "date") String date,
                                             @RequestParam(value = "time") String time,
                                             @RequestParam(value = "doctorID") String doctorID,
                                             Model mode) {
        try {
            log.info("--> appointment saved id="+id+" date="+date+" time="+time+" <--");
            appointmentService.saveAppointments(id, date, time, doctorID);
        } catch (DataIntegrityViolationException e) {
            return "error/time-reserved";
        }


        return "redirect:/";

    }

    @GetMapping("/profile/history")
    public String getPatientTreatmentHistory(Model model) {
        Optional<Patient> patientOptional = patientService.findPatienByAuthEmail();
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            Optional<PatientCard> patientCardOptional = patientService.findPatientCardByPatient(patient);
            if (patientCardOptional.isPresent()) {
                PatientCard patientCard = patientCardOptional.get();
                Set<TreatmentInformation> treatmentInformation = patientCard.getTreatmentInformation();
                model.addAttribute("treatmentInformation", treatmentInformation);
            }
        }
        return "patient/patientTreatment";

    }

}
