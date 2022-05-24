package com.evgeniy.commands;

import com.evgeniy.entity.*;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class MyAppointments implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {

        List<ReplyButton> replyButtonList = List.of(new ReplyButton("Главное меню"));

        executionContext.buildReplyKeyboard("Мои записи к врачу", replyButtonList);


        Optional<DataUserTg> dataUserByChatId = executionContext.getDataUserService().findDataUserByChatId(executionContext.getChatId());
        Patient patientByEmail = executionContext.getPatientService().findPatientByEmail(dataUserByChatId.get().getEmail());

        executionContext.getAppointmentService().findAllByPatientId(patientByEmail.getId()).forEach(e->executionContext.replyMessage(e.getDoctor().getFio()+"\n"+e.getDate()+"\n"+e.getTime()));


        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


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