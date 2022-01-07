package com.evgeniy.dev.controllers;

import com.evgeniy.dev.date.Date;
import com.evgeniy.dev.dbFile.repository.AuthorizationRepository;
import com.evgeniy.dev.dbFile.repository.ContactRepository;
import com.evgeniy.dev.dbFile.repository.DateRepository;
import org.postgresql.util.PSQLException;
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
    public String login(Model model) {
        return "clinic";
    }

    //POST
    @PostMapping("/clinic")
    public String postLogin(@RequestParam(value = "date") String date, @RequestParam(value = "time") String time, @RequestParam(value = "personfio") String personFio, @RequestParam(value = "client") String clientFullName, Model model) {
        Date incoming = new Date(date, time, personFio, clientFullName);
//        Iterable<Date> dateIterable = dateRepository.findAll();
//        AtomicBoolean flag = new AtomicBoolean(false);
//        dateIterable.forEach(xyi -> {
//            if ((xyi.getDate().equals(incoming.getDate())) && (xyi.getTime().equals(incoming.getTime()))) {
//                flag.set(true);
//            }
//            System.out.println("flag=" + flag);
//
////            dateRepository.save(incoming);
//        });
//        if (!flag.get()) {
//            dateRepository.save(incoming);
//        }
        try {
            dateRepository.save(incoming);
        } catch (DataIntegrityViolationException e) {

            return "test";
        }

        return "redirect:/";
    }


}