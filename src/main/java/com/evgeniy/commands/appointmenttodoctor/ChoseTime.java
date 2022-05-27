package com.evgeniy.commands.appointmenttodoctor;

import com.evgeniy.commands.localState.LocalStateForAppointment;
import com.evgeniy.telegram.ExecutionContext;

import java.sql.Time;

public class ChoseTime implements Appointment {


    @Override
    public void execute(ExecutionContext executionContext, LocalStateForAppointment localStateForAppointment) {
        Long docId = localStateForAppointment.getDoctorId();
        String inputMessage = executionContext.getInputText();
        executionContext.createAppointmentToDoctor(localStateForAppointment.getDate(), Time.valueOf(inputMessage + ":00"), String.valueOf(docId),executionContext);
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);

    }
}
