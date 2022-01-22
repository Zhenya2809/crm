package com.evgeniy.service;

import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;

    public List<AppointmentToDoctors> toDoctorsList(){
        return appointmentRepository.findAll();
    }
}
