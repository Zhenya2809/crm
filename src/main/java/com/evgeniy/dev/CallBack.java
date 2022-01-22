package com.evgeniy.dev;

import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.repository.AppointmentRepository;
import com.evgeniy.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CallBack {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AppointmentService appointmentService;
    AppointmentMapper appointmentMapper;
    public void printList(){

        Date date = new Date();
        System.out.println(date);

        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        System.out.println("Текущая дата " + formatForDateNow.format(date));

        List<AppointmentToDoctors> appointmentToDoctorsList = appointmentService.toDoctorsList();
        List<AppointmentDTO> appointmentDTOS = appointmentMapper.infoToInfoDTO(appointmentToDoctorsList);
        appointmentDTOS.forEach(System.out::println);

    }
}
