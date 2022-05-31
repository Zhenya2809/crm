package com.evgeniy.commands.usercommands;

import com.evgeniy.commands.Command;
import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Cosmetics implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext)  {
        executionContext.setGlobalState(DataUserTg.botstate.COSMETICS);

        List<ReplyButton> replyButtonList = List.of(new ReplyButton("текст1"),new ReplyButton("Главное меню"));
        executionContext.buildReplyKeyboard("TextToKeyboard", replyButtonList);
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Косметика");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.COSMETICS;
    }
}
