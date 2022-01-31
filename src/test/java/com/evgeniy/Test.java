package com.evgeniy;

import com.evgeniy.dev.AppointmentDTO;
import com.evgeniy.dev.AppointmentMapper;
import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.repository.AppointmentRepository;
import com.evgeniy.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Test {

    public static void main(String[] args) {

        Random r = new Random();
        int randomInt = r.nextInt(100) + 1;


        String[] x = generateRandomWords(100);
        System.out.println(x[randomInt].toString());
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
