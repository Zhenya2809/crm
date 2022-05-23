package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MyAppointments implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {

        List<ReplyButton> replyButtonList = List.of(new ReplyButton("Наш адрес"));

        executionContext.buildReplyKeyboard("Мои записи к врачу", replyButtonList);
        Optional<DataUserTg> dataUserByChatId = executionContext.getDataUserService().findDataUserByChatId(executionContext.getChatId());
        if(dataUserByChatId.isPresent()){
            executionContext.getUserService().findUserByUsername(dataUserByChatId.get().getEmail());
        }
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Мои записи");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.MY_APPOINTMENTS;
    }
}