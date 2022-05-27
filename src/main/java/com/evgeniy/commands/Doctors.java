package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.service.DoctorService;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class Doctors implements Command {
    @Autowired
    public DoctorService doctorService;

    @Override
    public void doCommand(ExecutionContext executionContext) {


//        doctorService.findAll().forEach(e -> {
//            String speciality = e.getSpeciality();
//            String fio = e.getFio();
//            executionContext.replyImage(e.getPhoto());
//            executionContext.replyMessage(e.getAbout());
//            executionContext.replyMessage(speciality + " " + fio);
//        });
        List<ReplyButton> replyButtonList = List.of(new ReplyButton("Записаться к доктору"),
                new ReplyButton("Специалисты"),
                new ReplyButton("Услуги"),
                new ReplyButton("Наш адрес"),
                new ReplyButton("Главное меню"));
        executionContext.buildReplyKeyboard("Хотите записаться на прием?", replyButtonList);
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Доктора");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.DOCTORS;
    }
}