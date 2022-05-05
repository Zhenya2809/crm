package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.telegram.ExecutionContext;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public interface Command {

    void doCommand(ExecutionContext context) throws TelegramApiException, IOException, Exception;
    boolean shouldRunOnText(String text);
    DataUserTg.botstate getGlobalState();
}
