package com.evgeniy.commands.registration;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.telegram.ExecutionContext;

import java.util.Optional;

public class GetEmailRegistration implements Registration {
    @Override
    public void execute(ExecutionContext executionContext) {
        String inputMessage = executionContext.getUpdate().getMessage().getText();

        DataUserTg user = executionContext.getAuthorizationUser();
        if (inputMessage.contains("@")) {
            user.setEmail(inputMessage);
            executionContext.getDataUserService().save(user);
            executionContext.getContactKeyboard();
            executionContext.setLocalState("get_email_and_phone_registration");
        } else {
            executionContext.replyMessage("Email не коректный, введите ещё раз");
        }

    }
}
