package com.evgeniy.controller;

import com.evgeniy.entity.Date;
import com.evgeniy.repository.ContactRepository;
import com.evgeniy.repository.DateRepository;
import com.evgeniy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class MainController {
    HashMap<String, String> usersMap = new HashMap<>();
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private DateRepository dateRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(@RequestParam(required = false) String time, Model model) {

        Iterable<Date> date = dateRepository.findAll();
        model.addAttribute("date", date);


        return "home";
    }

    @GetMapping("/price")
    public String price(Model model) {
        return "price-main";
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

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    //POST
    @PostMapping("/clinic")
    public String postClinic(@RequestParam(value = "date") String date,
                             @RequestParam(value = "time") String time,
                             @RequestParam(value = "personfio") String personFio,
                             @RequestParam(value = "client") String clientFullName, Model model) {


        Date incoming = new Date(date, time, personFio, clientFullName);
        try {
            dateRepository.save(incoming);
        } catch (DataIntegrityViolationException e) {
            return "time-reserved";
        }
        return "redirect:/";
    }


    @PostMapping("/registration")
    public String postRegistration(@RequestParam(value = "email") String email,
                                   @RequestParam(value = "password") String password,
                                   @RequestParam(value = "re-password") String rePassword,
                                   @RequestParam(value = "firstname") String firstname,
                                   @RequestParam(value = "lastname") String lastName,
                                   @RequestParam(value = "login") String login, Model model) {

        return "redirect:/";
    }

    //--------------------------------------------
    @PostMapping("/login")
    public String login(@RequestParam(value = "login") String login,
                        @RequestParam(value = "password") String password,
                        Model model) {


        return "redirect:/";
    }

}
