package com.evgeniy;

import com.evgeniy.dev.AppointmentDTO;
import com.evgeniy.dev.AppointmentMapper;
import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.repository.AppointmentRepository;
import com.evgeniy.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test {
    @Autowired
    static
    AppointmentRepository appointmentRepository;
    @Autowired
    AppointmentService appointmentService = new AppointmentService();
    AppointmentMapper appointmentMapper;

    public static void main(String[] args) {
        Iterable<AppointmentToDoctors> date = appointmentRepository.findAll();
        date.forEach(System.out::println);
    }
//    public void printList(){
//
//        Date date = new Date();
//        System.out.println(date);
//
//        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
//        System.out.println("Текущая дата " + formatForDateNow.format(date));
//
//        List<AppointmentToDoctors> appointmentToDoctorsList = appointmentService.toDoctorsList();
//        List<AppointmentDTO> appointmentDTOS = appointmentMapper.infoToInfoDTO(appointmentToDoctorsList);
//        appointmentDTOS.forEach(System.out::println);
//
//    }
}
