package com.evgeniy.commands.appointmenttodoctor;

import com.evgeniy.commands.Command;
import com.evgeniy.commands.appointmenttodoctor.localstate.LocalStateForAppointment;
import com.evgeniy.entity.*;
import com.evgeniy.telegram.ExecutionContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class AppointmentToDoctor implements Command {

    @Override
    public void doCommand(ExecutionContext executionContext) {

        try {

            String localState = executionContext.getLocalState();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();

            if (localState == null) {
                LocalStateForAppointment localStateForAppointment = new LocalStateForAppointment(null, "send_doctor_speciality", null);
                executionContext.setLocalState(objectMapper.writeValueAsString(localStateForAppointment));
            }
            LocalStateForAppointment localStateForAppointment = objectMapper.readValue(executionContext.getLocalState(), LocalStateForAppointment.class);
            String step = localStateForAppointment.getStep();

            Map<String, Appointment> appointmentMap = new HashMap<>();
            appointmentMap.put("send_doctor_speciality", new SendDoctorSpeciality());
            appointmentMap.put("chose_doctor", new ChoseDoctor());
            appointmentMap.put("chose_id", new ChoseId());
            appointmentMap.put("chose_data_to_appointment", new ChoseDataToAppointment());
            appointmentMap.put("chose_time", new ChoseTime());

            Appointment appointment = appointmentMap.get(step);
            if (appointment == null) {
                throw new RuntimeException("fail to find by step " + step);
            }
            appointment.execute(executionContext, localStateForAppointment);
            executionContext.setLocalState(objectMapper.writeValueAsString(localStateForAppointment));


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Записаться к доктору");
    }


    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.APPOINTMENT_TO_DOCTOR;
    }
}