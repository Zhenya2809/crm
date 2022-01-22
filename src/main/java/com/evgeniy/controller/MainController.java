package com.evgeniy.controller;

import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.entity.User;
import com.evgeniy.repository.ContactRepository;
import com.evgeniy.repository.AppointmentRepository;
import com.evgeniy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(@RequestParam(required = false) String time, Model model) {

        Iterable<AppointmentToDoctors> date = appointmentRepository.findAll();
        model.addAttribute("date", date);


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
                             @RequestParam(value = "personfio") String personFio,
                             @RequestParam(value = "client") String clientFullName,
                             Model model) {


//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        AppointmentToDoctors incoming = new AppointmentToDoctors();
//        incoming.setDate(date);
//        incoming.setTime(time);
//        incoming.setPersonFio(personFio);
//        incoming.setClientFullName(clientFullName);
//        incoming.setEmail(auth.getName());
        try {

            appointmentRepository.save(null);
        } catch (DataIntegrityViolationException e) {
            return "time-reserved";
        }
        return "redirect:/";
    }


}
