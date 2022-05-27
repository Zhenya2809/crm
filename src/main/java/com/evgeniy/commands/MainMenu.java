package com.evgeniy.commands;

import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.Patient;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MainMenu implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {


        Patient patient = executionContext.getPatientService().findPatientByEmail(executionContext.getDataUserService()
                .findDataUserByChatId(executionContext.getChatId()).get().getEmail(),executionContext);
        List<AppointmentToDoctors> allByPatientId = executionContext.getAppointmentService().findAllByPatientId(patient.getId());
        if (allByPatientId.size() > 0) {
            List<ReplyButton> replyButtonList = List.of(new ReplyButton("О нас"),
                    new ReplyButton("Специалисты"),
                    new ReplyButton("Услуги"),
                    new ReplyButton("Наш адрес"),
                    new ReplyButton("Мои записи"));

            executionContext.buildReplyKeyboard("О, как так? Тогда нам есть о чем поговорить! \n" +
                    "так хочеться рассказать тебе о нас", replyButtonList);
        } else {

            List<ReplyButton> replyButtonList = List.of(new ReplyButton("О нас"),
                    new ReplyButton("Специалисты"),
                    new ReplyButton("Услуги"),
                    new ReplyButton("Наш адрес"));

            executionContext.buildReplyKeyboard("О, как так? Тогда нам есть о чем поговорить! \n" +
                    "так хочеться рассказать тебе о нас", replyButtonList);
        }
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Главное меню");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.MAIN_MENU;
    }
}