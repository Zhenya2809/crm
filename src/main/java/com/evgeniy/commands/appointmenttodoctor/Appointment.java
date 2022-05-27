package com.evgeniy.commands.appointmenttodoctor;

import com.evgeniy.commands.localState.LocalStateForAppointment;
import com.evgeniy.telegram.ExecutionContext;

public interface Appointment {
   void execute(ExecutionContext executionContext, LocalStateForAppointment localStateForAppointment );
}
