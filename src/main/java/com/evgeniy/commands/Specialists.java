package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class Specialists implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext)  {
        executionContext.setGlobalState(DataUserTg.botstate.SPECIALISTS);

        executionContext.sendKeyboardSpecialists();
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Специалисты");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.SPECIALISTS;
    }
}