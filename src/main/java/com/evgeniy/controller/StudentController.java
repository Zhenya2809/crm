package com.evgeniy.controller;

import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.entity.Student;
import com.evgeniy.entity.User;
import com.evgeniy.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    //create-random-student


    @GetMapping("/create-random-student")
    public String getRandomStudent(Model model) {
        return "create-random-student";
    }
    @GetMapping("/student")
    public String getStudent(Model model) {
        Iterable<Student> student = studentRepository.findAll();
        model.addAttribute("students", student);


        return "student";
    }

    @PostMapping("/create-random-student")
    public String addRandomStudent(Model model) {
        String[] strings = generateRandomWords(100);
        Random r = new Random();
        int randomInt = r.nextInt(100) + 1;

        Student student = new Student();
        student.setName(strings[randomInt]);
        student.setEmail(strings[randomInt]+"@"+strings[randomInt]);

        studentRepository.save(student);


        return "redirect:/";
    }

    public static String[] generateRandomWords(int numberOfWords) {
        String[] randomStrings = new String[numberOfWords];
        Random random = new Random();
        for (int i = 0; i < numberOfWords; i++) {
            char[] word = new char[random.nextInt(8) + 3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
            for (int j = 0; j < word.length; j++) {
                word[j] = (char) ('a' + random.nextInt(26));
            }
            randomStrings[i] = new String(word);
        }
        return randomStrings;
    }
}
