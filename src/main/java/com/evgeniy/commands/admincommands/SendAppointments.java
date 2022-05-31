package com.evgeniy.commands.admincommands;

import com.evgeniy.commands.Command;
import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.Doctor;
import com.evgeniy.entity.Patient;
import com.evgeniy.service.AppointmentService;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class SendAppointments implements Command {
    @Autowired
    private AppointmentService appointmentService;
    @Override
    public void doCommand(ExecutionContext executionContext) {
        java.util.Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        List<AppointmentToDoctors> all = appointmentService.findAll().stream().filter(e -> e.getDate().toString().equals(formatForDateNow.format(date))).toList();
        all.forEach(e -> {


            Long chatId = e.getPatient().getChatId();
            executionContext.sendMessageToUserWithId("Напоминаем у вас запись к: \n"+e.getDoctor().getFio() + "\n" + e.getDate() + "\n" + e.getTime(), String.valueOf(chatId));
        });


    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Отправить уведомления");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.SEND_APPOINTMENT;
    }
}