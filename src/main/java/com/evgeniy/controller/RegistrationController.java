package com.evgeniy.controller;

import com.evgeniy.entity.User;
import com.evgeniy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@Slf4j
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @GetMapping("/account-registred")
    public String accountRegistred(Model model) {
        log.info("accountRegistred");
        return "account-registred";
    }


    @RequestMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }
    //POST

    @PostMapping("/registration")
    public String addUser(@RequestParam(value = "username") String username,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "passwordConfirm") String passwordConfirm,
                          @ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

        User user = userService.createUser(username, password, passwordConfirm);
        log.info("Registration user with name " + username);
        if (bindingResult.hasErrors()) {
            log.error("bindingResult has error");
            return "error/error";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            log.error("Passwords do not match");
            return "error/passwordIncorect";
        }
        try {
            log.info("user " + user + "successfully created");
            userService.saveUser(user);
            return "redirect:/";
        }catch (Exception e){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            log.error("A user with the same name already exists");
            return "error/usernameAlready";
        }
    }
}