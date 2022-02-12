package com.evgeniy.service;

import com.evgeniy.email.SendEmailTLS;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;


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

    public void sendEmailReminder() {

        SendEmailTLS sendEmailTLS = new SendEmailTLS();

        appointmentRepository.findAll().forEach(e -> {
            String date = e.getDate();
            String time = e.getTime();
            String email = e.getPatient().getEmail();
            Date todayDate = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Текущая дата " + formatForDateNow.format(todayDate));


            System.out.println("today date: " + formatForDateNow.format(todayDate) + " withDB date= " + date);

            if (formatForDateNow.format(todayDate).equals(date)) {
                System.out.println(todayDate + " equals " + date);
                sendEmailTLS.SendEmail("Clinic appointment reminder", email, "We remind you that you have an appointment with a clinic at " + time);
            }
        });

    }

    public Iterable<AppointmentToDoctors> findAll() {
        return appointmentRepository.findAll();
    }

    public Iterable<AppointmentToDoctors> findAppointmentToDoctorsByDoctor(Doctor doctor) {
        return appointmentRepository.findAppointmentToDoctorsByDoctor(doctor);
    }

    public Iterable<AppointmentToDoctors> findAllByDoctor_Id(Long id) {

        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        return appointmentRepository.findAllByDoctor_Id(id).stream().filter(e -> e.getDate().equals(formatForDateNow.format(date))).toList();
    }

    public List<AppointmentToDoctors> findAllByPatientId(Long id) {
        return appointmentRepository.findAllByPatientId(id);
    }

    public void deleteAppointmentByDoctorId(Long id) {
        AppointmentToDoctors appointmentToDoctorsByDoctorsappointmentsID = appointmentRepository.findAppointmentToDoctorsByDoctorsappointmentsID(id);
        appointmentRepository.delete(appointmentToDoctorsByDoctorsappointmentsID);
    }

    public AppointmentToDoctors findAppointmentById(Long id) {
        return appointmentRepository.findAppointmentToDoctorsByDoctorsappointmentsID(id);
    }

    public void saveAppointments(Long id, String date, String time, String doctorID) {
        AppointmentToDoctors appointment = appointmentRepository.findAppointmentToDoctorsByDoctorsappointmentsID(id);
        appointment.setDate(date);
        appointment.setTime(time);
        Doctor doctor = doctorRepository.findDoctorById(Long.valueOf(doctorID));
        appointment.setDoctor(doctor);
        appointmentRepository.save(appointment);
    }

}

