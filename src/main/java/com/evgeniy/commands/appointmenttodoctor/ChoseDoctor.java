package com.evgeniy.commands.appointmenttodoctor;

import com.evgeniy.commands.localState.LocalStateForAppointment;
import com.evgeniy.entity.Doctor;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;

import java.util.List;

public class ChoseDoctor implements Appointment {

    @Override
    public void execute(ExecutionContext executionContext, LocalStateForAppointment localStateForAppointment) {

        String inputMessage = executionContext.getUpdate().getMessage().getText();

        List<Doctor> doctorsBySpeciality = executionContext.getDoctorService().findDoctorsBySpeciality(inputMessage);
                doctorsBySpeciality.forEach(e -> {
            String speciality = e.getSpeciality();
            String fio = e.getFio();
            executionContext.replyImage(e.getPhoto());
            executionContext.replyMessage(e.getAbout());
            executionContext.replyMessage(speciality + " " + fio);
        });
        List<ReplyButton> doctorsFIOListForButton = doctorsBySpeciality.stream().map(e -> new ReplyButton(e.getFio())).toList();
        executionContext.buildReplyKeyboard("Выберите доктора", doctorsFIOListForButton);
        localStateForAppointment.setStep("chose_id");


    }
}
