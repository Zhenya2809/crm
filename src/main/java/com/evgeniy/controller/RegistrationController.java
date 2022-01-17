package com.evgeniy.controller;

import com.evgeniy.entity.User;
import com.evgeniy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }
    //POST

    @PostMapping("/registration")
    public String addUser(@RequestParam(value = "username") String username,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "passwordConfirm") String passwordConfirm,
                          @ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {
//        List<User> userList = userService.allUsers();
//        boolean usernameError = userList.stream().anyMatch(user -> user.getUsername().equals(username));
//

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPasswordConfirm(passwordConfirm);
        userService.saveUser(user);


        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.saveUser(user)) {

            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        return "redirect:/";
    }
}