package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Start implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {
        List<ReplyButton> replyButtonList = List.of(new ReplyButton("Начнем \uD83D\uDE09"),
                                                    new ReplyButton("Покажи свой сайт \uD83C\uDF10"),
                                                    new ReplyButton("О нас"));

        executionContext.buildReplyKeyboard("Привет " + executionContext.getFirstName() + "\n" +
                "Я виртуальный помощник современного медицинского центра красоты и здоровья CLINIC_NAME\n" +
                "Чем могу вам помочь?", replyButtonList);
    executionContext.setLocalState(null);
    }



    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("/start");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.START;
    }
}