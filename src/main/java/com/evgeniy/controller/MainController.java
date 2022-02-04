package com.evgeniy.controller;

import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.entity.Doctor;
import com.evgeniy.entity.Patient;
import com.evgeniy.repository.AppointmentRepository;
import com.evgeniy.repository.DoctorRepository;
import com.evgeniy.repository.PatientRepository;
import com.evgeniy.repository.UserRepository;
import com.evgeniy.service.AppointmentService;
import com.evgeniy.service.AppointmentToDoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
public class MainController {


    @Autowired
    private AppointmentToDoctorsService appointmentToDoctorsService;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/")
    public String home(@RequestParam(required = false) String time, Model model) {

        Iterable<AppointmentToDoctors> infoAppointmentToDoctor = appointmentService.findAll();

        model.addAttribute("date", infoAppointmentToDoctor);

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
            appointmentToDoctorsService.CreateAppointmentToDoctors(date, time, doctorID);
        } catch (DataIntegrityViolationException e) {
            return "time-reserved";
        }
        return "redirect:/";
    }


}
