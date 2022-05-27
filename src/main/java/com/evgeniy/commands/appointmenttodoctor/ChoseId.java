package com.evgeniy.commands.appointmenttodoctor;

import com.evgeniy.commands.localState.LocalStateForAppointment;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;

import java.time.LocalDate;
import java.util.List;

public class ChoseId implements Appointment {


    @Override
    public void execute(ExecutionContext executionContext, LocalStateForAppointment localStateForAppointment) {


        LocalDate today = LocalDate.now();
        String inputMessage = executionContext.getUpdate().getMessage().getText();
        localStateForAppointment.setDoctorId(executionContext.getDoctorService().findDoctorByFio(inputMessage).getId());
        List<ReplyButton> replyButtons = List.of(new ReplyButton(today.toString()),
                new ReplyButton(today.plusDays(1).toString()),
                new ReplyButton(today.plusDays(2).toString()),
                new ReplyButton(today.plusDays(3).toString()),
                new ReplyButton(today.plusDays(4).toString()));
        executionContext.buildReplyKeyboard("На какое число вы хотите записаться?", replyButtons);
        localStateForAppointment.setStep("chose_data_to_appointment");


    }
}
