package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class StartChattingWithBot implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext)  {
        executionContext.setGlobalState(DataUserTg.botstate.START_BOT_CHATTING);
        executionContext.sendKeyboardChoseYesOrNo();
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