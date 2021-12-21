package com.crm.crm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
//    (@RequestParam(name="name", required=false, defaultValue="World") String name)
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }
    @GetMapping("/about")
//    (@RequestParam(name="name", required=false, defaultValue="World") String name)
    public String about(Model model) {
        model.addAttribute("title", "Главная страница");
        return "about";
    }

}