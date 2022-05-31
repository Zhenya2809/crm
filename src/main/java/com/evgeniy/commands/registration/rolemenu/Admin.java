package com.evgeniy.commands.registration.rolemenu;

import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;

import java.util.List;

public class Admin implements ChoseRole {
    @Override
    public void execute(ExecutionContext executionContext) {
        List<ReplyButton> replyButtonList = List.of(
                new ReplyButton("Вход"),
                new ReplyButton("Отправить уведомления"));
        executionContext.buildReplyKeyboard("Привет " + executionContext.getLastName() + " \n" +
                "не забудь войти, выйти с акаунта", replyButtonList);

    }
}
