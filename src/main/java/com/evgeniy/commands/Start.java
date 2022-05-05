package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.telegram.ExecutionContext;

public class Start implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {
        executionContext.setGlobalState(DataUserTg.botstate.START);
        executionContext.sendKeyboardMainMenu();
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