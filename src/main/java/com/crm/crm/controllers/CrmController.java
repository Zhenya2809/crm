package com.crm.crm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CrmController {
    @GetMapping("/")
    public String crmMain(Model model){
return "home";
    }
}
