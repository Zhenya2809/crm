package com.evgeniy.commands.registration.rolemenu;

import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;

import java.util.List;

public class Doctor implements ChoseRole{
    @Override
    public void execute(ExecutionContext executionContext) {
        List<ReplyButton> replyButtonList = List.of(new ReplyButton("Записи на сегодня"),
                new ReplyButton("Мои приемы"));
        executionContext.buildReplyKeyboard("Доброе утро доктор " + executionContext.getLastName() + " \n" +
                "так хочеться рассказать тебе о нас", replyButtonList);
    }
}
