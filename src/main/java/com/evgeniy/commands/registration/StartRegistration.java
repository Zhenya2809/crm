package com.evgeniy.commands.registration;

import com.evgeniy.telegram.ExecutionContext;

public class StartRegistration implements Registration{
    @Override
    public void execute(ExecutionContext executionContext) {
        executionContext.replyMessage("Для того что бы продолжить, мне нужно с тобой познакомиться \n" +
                "Введите email");
        executionContext.setLocalState("get_email_and_phone_registration");
    }
}
