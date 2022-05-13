package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartChattingWithBot implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext)  {
        executionContext.setGlobalState(DataUserTg.botstate.START_BOT_CHATTING);
        List<ReplyButton> replyButtonList = List.of(new ReplyButton("Да"),
                                                    new ReplyButton("Нет"));
        List<String> keyboardRowList = List.of("Да", "Нет");
        executionContext.buildReplyKeyboard("Вы были у нас раньше?", replyButtonList);
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


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