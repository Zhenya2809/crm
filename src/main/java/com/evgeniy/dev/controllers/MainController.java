package com.evgeniy.dev.controllers;

import com.evgeniy.dev.date.Date;
import com.evgeniy.dev.date.Day;
import com.evgeniy.dev.date.Month;
import com.evgeniy.dev.date.Time;
import com.evgeniy.dev.dbFile.repository.AuthorizationRepository;
import com.evgeniy.dev.dbFile.repository.ContactRepository;
import com.evgeniy.dev.dbFile.repository.DateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
//    (@RequestParam(name="name", required=false, defaultValue="World") String name)
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

    @GetMapping("/login")
    public String login(Model model) {
        return "logg";
    }

    //пример
    @PostMapping("/login")
    public String postLogin(@RequestParam String date, @RequestParam String value, Model model) {
        Date incoming=new Date(date,value);
        dateRepository.save(incoming);
        return "redirect:/";
    }

}