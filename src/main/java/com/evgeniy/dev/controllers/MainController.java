package com.evgeniy.dev.controllers;

import com.evgeniy.dev.date.Date;
import com.evgeniy.dev.dbFile.repository.AuthorizationRepository;
import com.evgeniy.dev.dbFile.repository.ContactRepository;
import com.evgeniy.dev.dbFile.repository.DateRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private DateRepository dateRepository;
    @Autowired
    private AuthorizationRepository authorizationRepository;

    @GetMapping("/")
    public String home(Model model) {
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
    public String login(Model model) {
        return "clinic";
    }

    //пример
    @PostMapping("/login")
    public String postLogin(@RequestParam(value = "date") String date, @RequestParam(value = "time") String time, @RequestParam(value = "personfio") String personFio, @RequestParam(value = "client") String clientFullName, Model model) {
        Date incoming = new Date(date, time, personFio, clientFullName);
        dateRepository.save(incoming);
        return "redirect:/";
    }
//    @PostMapping("/test")
//    public String postTest(@RequestParam(value="time") String time, @RequestParam(value="date") String date, Model model) {
//        Date incoming=new Date(time,date);
//        dateRepository.save(incoming);
//        return "redirect:/";
//    }

}