package com.evgeniy.controller;

import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.entity.Doctor;
import com.evgeniy.entity.Patient;
import com.evgeniy.repository.AppointmentRepository;
import com.evgeniy.repository.DoctorRepository;
import com.evgeniy.repository.PatientRepository;
import com.evgeniy.repository.UserRepository;
import com.evgeniy.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class MainController {


    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/")
    public String home(@RequestParam(required = false) String time, Model model) {

        Iterable<AppointmentToDoctors> infoAppointmentToDoctor = appointmentRepository.findAll();
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Patient patient = patientRepository.findByEmail(auth.getName());
        Doctor doctor = doctorRepository.findDoctorById(Long.valueOf(doctorID));


        AppointmentToDoctors incoming = new AppointmentToDoctors();
        incoming.setDate(date);
        incoming.setTime(time);
        incoming.setDoctor(doctor);
        incoming.setPatient(patient);


        try {

            appointmentRepository.save(incoming);
        } catch (DataIntegrityViolationException e) {
            return "time-reserved";
        }
        return "redirect:/";
    }


}
