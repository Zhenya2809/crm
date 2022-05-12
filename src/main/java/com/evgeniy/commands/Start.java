package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.telegram.ExecutionContext;
import com.evgeniy.telegram.MyAppBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Start implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {
        executionContext.setGlobalState(DataUserTg.botstate.START);
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);

        List<String> keyboardRowList = List.of("Начнем \uD83D\uDE09", "Покажи свой сайт \uD83C\uDF10", "О нас");
        executionContext.keyboad("Привет " + executionContext.getFirstName() + "\n" +
                "Я виртуальный помощник современного медицинского центра красоты и здоровья CLINIC_NAME\n" +
                "Чем могу вам помочь?", keyboardRowList);


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