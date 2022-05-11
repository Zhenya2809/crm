package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class Start implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext)  {
        executionContext.setGlobalState(DataUserTg.botstate.START);
        executionContext.sendKeyboardMainStart();
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


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