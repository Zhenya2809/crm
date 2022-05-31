package com.evgeniy.controller;

import com.evgeniy.entity.*;
import com.evgeniy.service.AppointmentService;
import com.evgeniy.service.DoctorService;
import com.evgeniy.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;
import java.time.LocalDate;
import java.util.*;

@Controller
@Slf4j
public class MainController {


    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/")
    public String getHome(Model model) {


//ForUsers
        Optional<Patient> patientOptional = patientService.findPatientByAuthEmail();
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            List<AppointmentToDoctors> userAppointmentToDoctor = appointmentService.findAllByPatientId(patient.getId());
            model.addAttribute("userAppointmentToDoctor", userAppointmentToDoctor);
//ForAdmins
            Iterable<AppointmentToDoctors> infoAppointmentToDoctor = appointmentService.findAll();
            model.addAttribute("date", infoAppointmentToDoctor);

            log.info("--> Home page <--");
//ForDoctors
        }


        return "home";
    }

    @PostMapping("/")
    public String postHome(Model model) {
        return "home";
    }

    @GetMapping("/function")
    public String function(Model model) {
        return "function";
    }


    @GetMapping("/user/appointment/{id}/delete")
    public String getAppointmentDelete(@PathVariable(value = "id") long id,
                                       Model mode) {
        log.info("--> /user/appointment/{id}/delete page <--");
        appointmentService.deleteAppointmentByDoctorId(id);
        log.info("--> deleted appointment with id=" + id + " <--");
        return "redirect:/";

    }

    @GetMapping("/user/appointment/{id}/edit")
    public String getAppointmentEdit(@PathVariable(value = "id") long id,
                                     Model mode) {
        log.info("--> /user/appointment/{id}/edit page<--");
        return "patient/appointmentEdit";

    }

    @GetMapping("/clinic/{doctor_id}/{date}/{time}")
    public String postDoctorChose(@PathVariable(value = "date") String date,
                                  @PathVariable(value = "time") String time,
                                  Model model) {
        model.addAttribute("date", date);
        model.addAttribute("time", time);
        return "clinic/appointmentAdd";
    }

    @PostMapping("/clinic/{doctor_id}/{date}/{time}")
    public String postAppointmentDDT(@PathVariable(value = "doctor_id") String id,
                                     @PathVariable(value = "date") String date,
                                     @PathVariable(value = "time") String time,
                                     Model mode) {
        appointmentService.createAppointmentToDoctors(date, Time.valueOf(time + ":00"), id);
        return "redirect:/";

    }

    @GetMapping("/clinic/{doctor_id}")
    public String getDoctorChose(Model model,
                                 @PathVariable(value = "doctor_id") long id) {


        HashMap<Date, List<String>> dateAndTimeMap = appointmentService.findAllAvailableTimeByDoctorId(id);
        for (Map.Entry<Date, List<String>> pair : dateAndTimeMap.entrySet()) {
            List<String> list = pair.getValue().stream().map(e -> {
                String[] split = e.split(":");
                return split[0] + ":" + split[1];
            }).toList();
            pair.setValue(list);
        }


        LocalDate today = LocalDate.now();
        ArrayList<DaySchedule> daySchedules = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate localDate = today.plusDays(i);
            DaySchedule daySchedule = new DaySchedule();
            String key = localDate.toString();
            daySchedule.setDate(key);
            if (dateAndTimeMap.get(java.sql.Date.valueOf(key)) != null) {
                daySchedule.setAvailable(new HashSet<>(dateAndTimeMap.get(java.sql.Date.valueOf(key))));
            } else {
                daySchedule.setAvailable(new HashSet<>());
            }
            daySchedules.add(daySchedule);
        }

        model.addAttribute("schedule", daySchedules);
        System.out.println("day schedules=" + daySchedules);
        List<String> timeList = new ArrayList<>();
        timeList.add("08:00");
        timeList.add("09:00");
        timeList.add("10:00");
        timeList.add("11:00");
        timeList.add("12:00");
        timeList.add("13:00");
        timeList.add("14:00");
        timeList.add("15:00");
        timeList.add("16:00");
        timeList.add("17:00");
        timeList.add("18:00");
        timeList.add("19:00");
        model.addAttribute("time", timeList);


        model.addAttribute("doctor_id", id);
        return "clinic/appointment";
    }

    @GetMapping("/clinic/chose_doctor")
    public String choseDoctor(Model model) {
        List<Doctor> doctorList = doctorService.findAll();
        model.addAttribute("doctorList", doctorList);


        return "clinic/doctor";
    }

    @GetMapping("/profile/history")
    public String getPatientTreatmentHistory(Model model) {
        Optional<Patient> patientOptional = patientService.findPatientByAuthEmail();
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            Optional<PatientCard> patientCardOptional = patientService.findPatientCardByPatient(patient);
            if (patientCardOptional.isPresent()) {
                PatientCard patientCard = patientCardOptional.get();
                Set<TreatmentInformation> treatmentInformation = patientCard.getTreatmentInformation();
                model.addAttribute("treatmentInformation", treatmentInformation);
            }
        }
        return "patient/patientTreatment";

    }

    @PostMapping("/user/appointment/{id}/edit")
    public String postAppointmentPatientEdit(@PathVariable(value = "id") long id,
                                             @RequestParam(value = "date") String date,
                                             @RequestParam(value = "time") String time,
                                             @RequestParam(value = "doctorID") String doctorID,
                                             Model mode) {
        try {
            log.info("--> appointment saved id=" + id + " date=" + date + " time=" + time + " <--");
            appointmentService.saveAppointments(id, date, time, doctorID);
        } catch (DataIntegrityViolationException e) {
            return "error/time-reserved";
        }


        return "redirect:/";

    }


}

