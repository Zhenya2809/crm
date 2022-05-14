package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.InlineButton;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.service.DoctorService;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppointmentToDoctor implements Command {
    @Autowired
    public DoctorService doctorService;

    @Override
    public void doCommand(ExecutionContext executionContext) {
        executionContext.setGlobalState(DataUserTg.botstate.APPOINTMENT_TO_DOCTOR);


        List<InlineButton> inlineButtons = List.of(new InlineButton("Терапевт", "http://95.216.146.138:8080/clinic/1"),
                                                   new InlineButton("Психолог", "http://95.216.146.138:8080/clinic/2"),
                                                   new InlineButton("Хирург", "http://95.216.146.138:8080/clinic/3"),
                                                   new InlineButton("Окулист", "http://95.216.146.138:8080/clinic/4"));
        executionContext.buildInlineKeyboard("перейти на сайт для записи", inlineButtons);
        List<ReplyButton> replyButtons= List.of(new ReplyButton("Терапевт"),
                                                new ReplyButton("Психолог"),
                                                new ReplyButton("Хирург"),
                                                new ReplyButton("Офтальмолог"));
        executionContext.buildReplyKeyboard("Продолжить через телеграмм",replyButtons);
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


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