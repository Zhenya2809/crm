package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class About implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {
        executionContext.setGlobalState(DataUserTg.botstate.ABOUT);

        executionContext.sendInlineKeyboardAbout();
        executionContext.sendKeyboardAbout();

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(executionContext.getChatId()));
        message.setText("Возможно тебя заинтересует одна из наших соц. сетей?");
        // Create InlineKeyboardMarkup object
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        //Создаём клавиатуру (list of InlineKeyboardButton list)
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        // Создаём лист для кнопок
        List<InlineKeyboardButton> Buttons = new ArrayList<InlineKeyboardButton>();
        // Инициализируем кнопку и текст к кнопке
        InlineKeyboardButton news = new InlineKeyboardButton("Instagram");
        // Нужно выбрать одно из опциональных полей и можна добавить его с помощью set method
        news.setUrl("https://instagram.com");
        // Добавляем кнопку в лист
        Buttons.add(news);
        // Инициализируем кнопку и текст к кнопке
        InlineKeyboardButton decrees = new InlineKeyboardButton("Facebook");
        // Нужно выбрать одно из опциональных полей и можна добавить его с помощью set method
        decrees.setUrl("https://facebook.com");
        // Добавляем кнопку в лист
        Buttons.add(decrees);
        // Инициализируем кнопку и текст к кнопке
        keyboard.add(Buttons);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(inlineKeyboardMarkup);

        executionContext.sendMessage(message);
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);
    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("О нас");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.ABOUT;
    }
}