package com.evgeniy.controller;

import com.evgeniy.entity.Doctor;
import com.evgeniy.entity.User;
import com.evgeniy.repository.DoctorRepository;
import com.evgeniy.service.AppointmentService;
import com.evgeniy.service.DoctorService;
import com.evgeniy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Iterator;

@Controller
public class AdminController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DoctorService doctorService;

    //admin
    @GetMapping("/admin")
    public String userList(Model model) {
        Iterator<User> allUsers = userService.allUsers().stream().iterator();
        model.addAttribute("allUsers", allUsers);
        return "admin";
    }

    @PostMapping("/admin")
    public String deleteUser(@RequestParam(value = "userID") Long userID,
                             Model model) {
        if (userID > 1) {
            userService.deleteUser(userID);
        }
        return "redirect:/admin";
    }


    //adminreminder
    @GetMapping("/adminreminder")
    public String adminReminder(Model model) {

        return "adminreminder";
    }

    @PostMapping("/adminreminder")
    public String deleteUser(@RequestParam(value = "sendEmail") String sendEmail,
                             Model model) {
        if (sendEmail.equals("1")) {
            appointmentService.sendEmailReminder();
        }
        return "redirect:/adminreminder";
    }

    //newdoctor
    @GetMapping("/admin/newdoctor")
    public String getNewDoctor(Model model) {
        Iterator<Doctor> allDoctors = doctorRepository.findAll().iterator();
        model.addAttribute("allDoctors", allDoctors);
        return "admin/newdoctor";
    }

    @PostMapping("/admin/newdoctor")
    public String postNewDoctor(@RequestParam(value = "doctorfio") String doctorfio,
                                @RequestParam(value = "speciality") String speciality,
                                Model model) {

        doctorService.createrDoctor(doctorfio, speciality);
        return "redirect:/admin";
    }

    //
//    @GetMapping("/admin/gt/{userId}")
//    public String gtUser(@PathVariable("userId") Long userId, Model model) {
//        model.addAttribute("allUsers", userService.usergtList(userId));
//        return "admin";
//    }
    //test css and scripts
    @GetMapping("/styled-page")
    public String getStyledPage(Model model) {
        model.addAttribute("name", "Baeldung Reader");
        return "cssandjs/styledPage";
    }

}
