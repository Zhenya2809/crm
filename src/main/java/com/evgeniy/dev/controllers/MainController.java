package com.evgeniy.dev.controllers;

import com.evgeniy.dev.dbFile.models.Users;
import com.evgeniy.dev.dbFile.models.ContactData;
import com.evgeniy.dev.dbFile.repository.AuthorizationRepository;
import com.evgeniy.dev.dbFile.repository.ContactRepository;
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
    private AuthorizationRepository authorizationRepository;

    @GetMapping("/")
//    (@RequestParam(name="name", required=false, defaultValue="World") String name)
    public String home(@RequestParam(required = false) String login, Model model) {

        Iterable<ContactData> contactData = contactRepository.findAll();
        model.addAttribute("contactData", contactData);
        return "home";
    }

    @GetMapping("/price")
    public String price(Model model) {
        return "price-main";
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

    @PostMapping("/login")
    public String postLogin(@RequestParam String email, @RequestParam String password, Model model) {
        Users users = new Users(email, password);
        authorizationRepository.save(users);
        return "redirect:/";
    }
}