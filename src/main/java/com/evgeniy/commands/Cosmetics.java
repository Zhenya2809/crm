package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.telegram.ExecutionContext;

public class Cosmetics implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext)  {
        executionContext.setGlobalState(DataUserTg.botstate.COSMETICS);


        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("НЕТ");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.COSMETICS;
    }
}
