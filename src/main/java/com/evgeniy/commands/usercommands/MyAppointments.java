package com.evgeniy.commands.usercommands;

import com.evgeniy.commands.Command;
import com.evgeniy.entity.*;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class MyAppointments implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {
        try {
            List<ReplyButton> replyButtonList = List.of(new ReplyButton("Главное меню"));
            executionContext.buildReplyKeyboard("Мои записи к врачу", replyButtonList);
            Optional<DataUserTg> dataUserByChatId = executionContext.getDataUserService().findDataUserByChatId(executionContext.getChatId());
            Patient patientByEmail = executionContext.getPatientService().findPatientByEmail(dataUserByChatId.get().getEmail(),executionContext);
            executionContext.getAppointmentService().findAllByPatientId(patientByEmail
                    .getId()).forEach(e -> {
                Instant now = Instant.now();
                Instant yesterday = now.minus(1, ChronoUnit.DAYS);
                Date myDate = Date.from(yesterday);
                if (e.getDate().after(myDate)) {
                    executionContext.replyMessage(e.getDoctor().getFio() + "\n" + e.getDate() + "\n" + e.getTime());
                }
            });
            executionContext.setLocalState(null);
            executionContext.setGlobalState(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Мои записи");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.MY_APPOINTMENTS;
    }
}