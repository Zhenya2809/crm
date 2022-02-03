package com.evgeniy.service;

import com.evgeniy.email.SendEmailTLS;
import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.entity.User;
import com.evgeniy.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;



    public void sendEmailReminder() {

        SendEmailTLS sendEmailTLS= new SendEmailTLS();

        appointmentRepository.findAll().forEach(e-> {
            String date = e.getDate();
            String time= e.getTime();
            String email = e.getPatient().getEmail();

            Date todayDate = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Текущая дата " + formatForDateNow.format(todayDate));


            System.out.println("today date: "+formatForDateNow.format(todayDate)+" withDB date= "+date);

            if(formatForDateNow.format(todayDate).equals(date)){
                System.out.println(todayDate+" equals "+date);
                sendEmailTLS.SendEmail("Clinic appointment reminder",email,"We remind you that you have an appointment with a clinic at "+ time);
            }
        });

    }


}
