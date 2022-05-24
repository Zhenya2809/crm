package com.evgeniy;

import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.entity.Doctor;
import com.evgeniy.entity.Patient;
import com.evgeniy.entity.User;
import com.evgeniy.repository.AppointmentRepository;
import com.evgeniy.repository.DoctorRepository;
import com.evgeniy.repository.PatientRepository;
import com.evgeniy.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class AppointmentTest {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Test
    void appointmentTest() {
//        AppointmentToDoctors appointment = new AppointmentToDoctors();

//        appointment.setDate("2022-05-25");
//        appointment.setTime("9:00");
//        Doctor doctorById = doctorRepository.findDoctorById(1L);
//        appointment.setDoctor(doctorById);
//        Patient patient = patientRepository.findByEmail("Zhenya.gricyk@gmail.com").get();
//        appointment.setPatient(patient);
//        appointmentRepository.save(appointment);

    }
}
