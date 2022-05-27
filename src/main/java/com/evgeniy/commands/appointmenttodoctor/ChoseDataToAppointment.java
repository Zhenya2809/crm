package com.evgeniy.commands.appointmenttodoctor;

import com.evgeniy.commands.localState.LocalStateForAppointment;
import com.evgeniy.telegram.ExecutionContext;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class ChoseDataToAppointment implements Appointment {

    @Override
    public void execute(ExecutionContext executionContext, LocalStateForAppointment localStateForAppointment) {


        LocalDate today = LocalDate.now();
        Long docId = localStateForAppointment.getDoctorId();
        String inputMessage = executionContext.getUpdate().getMessage().getText();
        localStateForAppointment.setDate(LocalDate.parse(inputMessage));
        extracted(executionContext, today, docId);
        localStateForAppointment.setStep("chose_time");


    }

    private void extracted(ExecutionContext executionContext, LocalDate today, Long docId) {
        List<String> strings = executionContext.freeTimeToAppointmentForDay(today, docId);
        executionContext.buildReplyKeyboardWithStringList("Выберите время", strings);
    }
}
