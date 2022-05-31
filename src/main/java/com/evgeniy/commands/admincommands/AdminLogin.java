package com.evgeniy.commands.admincommands;

import com.evgeniy.commands.Command;
import com.evgeniy.entity.DataUserTg;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class AdminLogin implements Command {
    @Override
    public void doCommand(ExecutionContext context) throws Exception {
        context.getInfoDataService().setAdministratorToday(context.getChatId());
        context.replyMessage("Вход успешный");
    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Вход");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.LOGIN;
    }
}
