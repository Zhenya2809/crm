package com.evgeniy.commands.registration.rolemenu;

import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;

import java.util.List;

public class User implements ChoseRole {
    @Override
    public void execute(ExecutionContext executionContext) {
        List<ReplyButton> replyButtonList = List.of(new ReplyButton("О нас"),
                new ReplyButton("Специалисты"),
                new ReplyButton("Заказать обратный звонок"),
                new ReplyButton("Услуги"),
                new ReplyButton("Наш адрес"));
        executionContext.buildReplyKeyboard("Нам есть о чем поговорить! \n" +
                "так хочеться рассказать тебе о нас", replyButtonList);
    }
}
