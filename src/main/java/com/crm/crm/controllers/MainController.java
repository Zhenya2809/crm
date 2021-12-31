package com.crm.crm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
//    (@RequestParam(name="name", required=false, defaultValue="World") String name)
    public String home(Model model) {
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

}