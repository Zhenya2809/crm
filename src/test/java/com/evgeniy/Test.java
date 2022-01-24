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

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);

        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(formatForDateNow.format(date));
    }
}
