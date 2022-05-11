package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.telegram.ExecutionContext;

public class MainMenu implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext)  {
        executionContext.setGlobalState(DataUserTg.botstate.MAIN_MENU);

        executionContext.sendKeyboardMainMenu();
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Главное меню");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.MAIN_MENU;
    }
}