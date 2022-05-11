package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class Yes implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {
        executionContext.setGlobalState(DataUserTg.botstate.YES);

        executionContext.sendKeyboardIfChoseYes();
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