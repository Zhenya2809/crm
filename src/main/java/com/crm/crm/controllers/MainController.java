package com.crm.crm.controllers;

import com.crm.crm.model.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private final ClientService clientService;

    public MainController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }
    @GetMapping("/price")
    public String price(Model model){
        return "price-main";
    }
    @GetMapping("/about")
    public String about(Model model){
        return "aboutUs";
    }
    @GetMapping("/function")
    public String function(Model model){
        return "function";
    }

}