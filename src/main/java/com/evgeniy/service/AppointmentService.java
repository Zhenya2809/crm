package com.evgeniy.service;

import com.evgeniy.email.SendEmailTLS;
import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.Doctor;
import com.evgeniy.entity.Patient;
import com.evgeniy.repository.AppointmentRepository;
import com.evgeniy.repository.DoctorRepository;
import com.evgeniy.repository.PatientRepository;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;


    public String createAppointmentToDoctors(String date, Time time, String doctorID) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<Patient> patientOptional = patientRepository.findByEmail(auth.getName());

        if (patientOptional.isPresent()) {
            try {
                Patient patient = patientOptional.get();
                Doctor doctor = doctorRepository.findDoctorById(Long.valueOf(doctorID));
                AppointmentToDoctors incoming = new AppointmentToDoctors();
                incoming.setDate(java.sql.Date.valueOf(date));
                incoming.setTime(time);
                incoming.setDoctor(doctor);
                incoming.setPatient(patient);

                appointmentRepository.save(incoming);
            } catch (DataIntegrityViolationException e) {
                System.out.println(" ERROR ---->  this date/time/doctor already exists  <----");
            }
            return "redirect:/";
        }
        return "patientIDisNullError";
    }

    public void createAppointmentTDoctors(String email, String date, Time time, String doctorID, ExecutionContext executionContext) {


        Optional<Patient> patientOptional = patientRepository.findByEmail(email);

        if (patientOptional.isPresent()) {

            Patient patient = patientOptional.get();
            Doctor doctor = doctorRepository.findDoctorById(Long.valueOf(doctorID));
            AppointmentToDoctors incoming = new AppointmentToDoctors();
            incoming.setDate(java.sql.Date.valueOf(date));
            incoming.setTime(time);
            incoming.setDoctor(doctor);
            incoming.setPatient(patient);
            appointmentRepository.save(incoming);
        } else {
            Optional<DataUserTg> dataUserByChatId = executionContext.getDataUserService().findDataUserByChatId(executionContext.getChatId());
            if (dataUserByChatId.isPresent()) {
                DataUserTg user = dataUserByChatId.get();
                Patient patient = new Patient();
                patient.setFio(user.getFirstName() + " " + user.getLastName());
                patient.setEmail(user.getEmail());
                patient.setPhoneNumber(user.getPhone());
                executionContext.getPatientService().save(patient);

                Doctor doctor = doctorRepository.findDoctorById(Long.valueOf(doctorID));
                AppointmentToDoctors incoming = new AppointmentToDoctors();
                incoming.setDate(java.sql.Date.valueOf(date));
                incoming.setTime(time);
                incoming.setDoctor(doctor);
                incoming.setPatient(patient);
                appointmentRepository.save(incoming);


            }
        }
    }

    public void sendEmailReminder() {

        SendEmailTLS sendEmailTLS = new SendEmailTLS();

        appointmentRepository.findAll().forEach(e -> {
            Date date = e.getDate();
            Time time = e.getTime();
            String email = e.getPatient().getEmail();
            Date todayDate = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Текущая дата " + formatForDateNow.format(todayDate));


            if (formatForDateNow.format(todayDate).equals(date)) {
                System.out.println(todayDate + " equals " + date);
                sendEmailTLS.SendEmail("Clinic appointment reminder", email, "We remind you that you have an appointment with a clinic at " + time);
                System.out.println("email=" + email + " apointment to time=" + time);
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

    public HashMap<Date, List<String>> findAllAvailableTimeByDoctorId(Long id) {
        List<AppointmentToDoctors> allByDoctor_id = appointmentRepository.findAllByDoctor_Id(id);


        return listToMap(allByDoctor_id);
    }

    public static HashMap<Date, List<String>> listToMap(List<AppointmentToDoctors> list) {
        HashMap<Date, List<String>> map = new HashMap<>();
        for (AppointmentToDoctors appointment : list) {
            if (map.containsKey(appointment.getDate())) {
                List<String> timeList = map.get(appointment.getDate());
                timeList.add(appointment.getTime().toString());
            } else {
                ArrayList<String> timeList = new ArrayList<>();
                timeList.add(appointment.getTime().toString());
                map.put(appointment.getDate(), timeList);
            }
        }
        return map;
    }

    public List<AppointmentToDoctors> findAllByPatientId(Long id) {
        return appointmentRepository.findAllByPatientId(id);
    }

    public void deleteAppointmentByDoctorId(Long id) {
        AppointmentToDoctors appointmentToDoctorsByDoctorsappointmentsID = appointmentRepository.findAppointmentToDoctorsByDoctorsappointmentsID(id);
        appointmentRepository.delete(appointmentToDoctorsByDoctorsappointmentsID);
    }

    public void saveAppointments(Long id, String date, String time, String doctorID) {
        AppointmentToDoctors appointment = appointmentRepository.findAppointmentToDoctorsByDoctorsappointmentsID(id);
        appointment.setDate(java.sql.Date.valueOf(date));
        appointment.setTime(Time.valueOf(time));
        Doctor doctor = doctorRepository.findDoctorById(Long.valueOf(doctorID));
        appointment.setDoctor(doctor);
        appointmentRepository.save(appointment);
    }

}

