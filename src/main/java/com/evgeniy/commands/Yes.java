package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Yes implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {
        executionContext.setGlobalState(DataUserTg.botstate.YES);
        List<ReplyButton> replyButtonList = List.of(new ReplyButton("Специалисты"),
                                                    new ReplyButton("Главное меню"));

        executionContext.buildReplyKeyboard("Какая услуга тебя интересует?", replyButtonList);

        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Да");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.YES;
    }
}