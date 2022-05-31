package com.evgeniy.commands.registration;

import com.evgeniy.commands.Command;
import com.evgeniy.commands.appointmenttodoctor.*;
import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.service.DataUserService;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class Authorization implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {
        try {
            String localState = executionContext.getLocalState();
            DataUserTg authorizationUser = executionContext.getAuthorizationUser();

            if ((authorizationUser.getEmail() != null) && (authorizationUser.getPhone() != null) && (localState == null)) {
                executionContext.setLocalState("authorized");
            }
            if ((localState == null) && (authorizationUser.getEmail() == null) && (authorizationUser.getPhone() == null)) {
                executionContext.setLocalState("start_registration");
            }
            String step = executionContext.getLocalState();
            Map<String, Registration> authorizationMap = new HashMap<>();
            authorizationMap.put("start_registration", new StartRegistration());
            authorizationMap.put("get_email_and_phone_registration", new GetEmailRegistration());
            authorizationMap.put("authorized", new Authorized());
            Registration registration = authorizationMap.get(step);
            if (registration == null) {
                throw new RuntimeException("fail to find by step " + step);
            }
            registration.execute(executionContext);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Начнем \uD83D\uDE09");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.START_BOT_CHATTING;
    }
}