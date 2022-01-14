package com.evgeniy.dev.controllers;

import com.evgeniy.dev.dbFile.models.Date;
import com.evgeniy.dev.dbFile.models.Users;
import com.evgeniy.dev.dbFile.repository.AuthorizationRepository;
import com.evgeniy.dev.dbFile.repository.ContactRepository;
import com.evgeniy.dev.dbFile.repository.DateRepository;
import com.evgeniy.dev.dbFile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.atomic.AtomicBoolean;

@Controller
public class MainController {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private DateRepository dateRepository;
    @Autowired
    private AuthorizationRepository authorizationRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(@RequestParam(required = false) String time, Model model) {

        Iterable<Date> date = dateRepository.findAll();
//        System.out.println(date);
//        date.forEach(xyi-> {
//            String clientFullName = xyi.getClientFullName();
//
//        });
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
        try {
            Users incomingUserData = new Users(email, password, firstname, lastName, login);
            AtomicBoolean flag = new AtomicBoolean(false);
            Iterable<Users> users = userRepository.findAll();
            users.forEach(element -> {
                if (element.getLogin().equals(incomingUserData.getLogin())) {
                    flag.set(true);
                }
            });

            if (!password.equals(rePassword)) {
                return "registration-password-error";
            }
            if (!flag.get()) {
                userRepository.save(incomingUserData);
            }
            if (flag.get()) {
                return "registration-login-error";
            }
        } catch (DataIntegrityViolationException e) {
            return "registration-password-error";
        }
        return "redirect:/";
    }
}