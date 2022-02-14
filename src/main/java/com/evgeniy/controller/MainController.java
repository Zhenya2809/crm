package com.evgeniy.controller;

import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.entity.Patient;
import com.evgeniy.entity.PatientCard;
import com.evgeniy.entity.TreatmentInformation;
import com.evgeniy.service.AppointmentService;
import com.evgeniy.service.DoctorService;
import com.evgeniy.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
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
        Patient patient = patientService.findPatienByAuthEmail();
        if (patient != null) {
            List<AppointmentToDoctors> userAppointmentToDoctor = appointmentService.findAllByPatientId(patient.getId());
            model.addAttribute("userAppointmentToDoctor", userAppointmentToDoctor);


//ForAdmins
            Iterable<AppointmentToDoctors> infoAppointmentToDoctor = appointmentService.findAll();
            model.addAttribute("date", infoAppointmentToDoctor);
//ForDoctors
        }
        return "home";
    }

    @PostMapping("/")
    public String postHome(Model model) {


        return "home";
    }

    @GetMapping("/price")
    public String price(Model model) {
        return "price-main";
    }

    @GetMapping("/news")
    public String news(Model model) {
        return "news";
    }

    @GetMapping("/test")
    public String test(Model model) {
        return "test";
    }


    @GetMapping("/about")
    public String about(Model model) {
        return "aboutUs";
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
        try {
            if (!date.equals("")) {
                appointmentService.createAppointmentToDoctors(date, time, doctorID);
            }
        } catch (DataIntegrityViolationException e) {
            return "time-reserved";
        }
        return "redirect:/";
    }

    @GetMapping("/user/appointment/{id}/delete")
    public String getAppointmentDelete(@PathVariable(value = "id") long id,
                                       Model mode) {

        appointmentService.deleteAppointmentByDoctorId(id);
        return "redirect:/";

    }

    @GetMapping("/user/appointment/{id}/edit")
    public String getAppointmentEdit(@PathVariable(value = "id") long id,
                                     Model mode) {
        return "patient/appointmentEdit";

    }

    @PostMapping("/user/appointment/{id}/edit")
    public String postAppointmentPatientEdit(@PathVariable(value = "id") long id,
                                             @RequestParam(value = "date") String date,
                                             @RequestParam(value = "time") String time,
                                             @RequestParam(value = "doctorID") String doctorID,
                                             Model mode) {
        try {
            appointmentService.saveAppointments(id, date, time, doctorID);
        } catch (DataIntegrityViolationException e) {
            return "time-reserved";
        }


        return "redirect:/";

    }

    @GetMapping("/profile/history")
    public String getPatientTreatmentHistory(Model model) {
        Patient patient = patientService.findPatienByAuthEmail();
        PatientCard patientCard = patientService.findPatientCardByPatient(patient);
        Set<TreatmentInformation> treatmentInformation = patientCard.getTreatmentInformation();
        model.addAttribute("treatmentInformation", treatmentInformation);

        return "patient/patientTreatment";

    }

}
