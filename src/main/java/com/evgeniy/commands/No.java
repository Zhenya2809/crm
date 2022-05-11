package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class No implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {
        executionContext.setGlobalState(DataUserTg.botstate.NO);

        executionContext.sendKeyboardMainMenu();
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Нет");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.NO;
    }
}