package com.evgeniy.service;

import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.entity.Doctor;
import com.evgeniy.entity.Patient;
import com.evgeniy.repository.AppointmentRepository;
import com.evgeniy.repository.DoctorRepository;
import com.evgeniy.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AppointmentToDoctorsService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;


    public void CreateAppointmentToDoctors(String date, String time, String doctorID) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Patient patient = patientRepository.findByEmail(auth.getName());
        Doctor doctor = doctorRepository.findDoctorById(Long.valueOf(doctorID));

        AppointmentToDoctors incoming = new AppointmentToDoctors();
        incoming.setDate(date);
        incoming.setTime(time);
        incoming.setDoctor(doctor);
        incoming.setPatient(patient);

        appointmentRepository.save(incoming);
    }


}
