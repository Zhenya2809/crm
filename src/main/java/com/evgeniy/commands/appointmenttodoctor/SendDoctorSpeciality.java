package com.evgeniy.commands.appointmenttodoctor;

import com.evgeniy.commands.appointmenttodoctor.localstate.LocalStateForAppointment;
import com.evgeniy.entity.Doctor;
import com.evgeniy.entity.InlineButton;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;

import java.util.List;

public class SendDoctorSpeciality implements Appointment {
    @Override
    public void execute(ExecutionContext executionContext, LocalStateForAppointment localStateForAppointment) {


        List<Doctor> all = executionContext.getDoctorService().findAll();
        List<InlineButton> inlineButtons = all.stream().map(doctor -> {
            String speciality = doctor.getSpeciality();
            Long id = doctor.getId();
            return new InlineButton(speciality, "http://95.216.146.138:8080/clinic/" + id);
        }).toList();
        List<ReplyButton> replyButtons = all.stream().map(e -> new ReplyButton(e.getSpeciality())).toList();
        executionContext.buildInlineKeyboard("перейти на сайт для записи", inlineButtons);
        executionContext.buildReplyKeyboard("Продолжить через телеграмм", replyButtons);
        localStateForAppointment.setStep("chose_doctor");


    }
}
