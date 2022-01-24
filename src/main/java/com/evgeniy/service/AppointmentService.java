package com.evgeniy.service;

import com.evgeniy.dev.AppointmentDTO;
import com.evgeniy.dev.AppointmentMapper;
import com.evgeniy.email.SendEmailTLS;
import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;

    public List<AppointmentToDoctors> toDoctorsList() {
        return appointmentRepository.findAll();
    }

    public void sendEmailReminder() {
        List<AppointmentToDoctors> appointmentToDoctorsList = appointmentRepository.findAll().stream().toList();
        SendEmailTLS sendEmailTLS= new SendEmailTLS();


        List<AppointmentDTO> appointmentDTOS = AppointmentMapper.INSTANCE.infoToInfoDTO(appointmentToDoctorsList);


        appointmentDTOS.forEach(element-> {
            Date todayDate = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Текущая дата " + formatForDateNow.format(todayDate));
            String date= element.getDate();
            String time = element.getTime();
            String email = element.getEmail();

            System.out.println("today date: "+formatForDateNow.format(todayDate)+" withDB date= "+date);

            if(formatForDateNow.format(todayDate).equals(date)){
                System.out.println(todayDate+" equals "+date);
                sendEmailTLS.SendEmail("Clinic appointment reminder",email,"We remind you that you have an appointment with a clinic at "+ time);
            }

        });

    }


}
