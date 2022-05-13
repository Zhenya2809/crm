package com.evgeniy.telegram;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.InlineButton;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.service.DataUserService;
import com.evgeniy.service.DoctorService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Getter
@Setter
@Slf4j

public class ExecutionContext {

    private MyAppBot myAppBot;
    public DataUserTg.botstate GlobalState;
    private Long chatId;
    private String firstName;
    private String lastName;
    private String inputText;
    private Update update;
    private DataUserService dataUserService;
    private DoctorService doctorService;

    public void setGlobalState(DataUserTg.botstate newState) {
        Optional<DataUserTg> dataUserByChatId = dataUserService.findDataUserByChatId(chatId);
        if (dataUserByChatId.isPresent()) {
            DataUserTg userTg = dataUserByChatId.get();
            userTg.setGlobalState(newState);
            dataUserService.save(userTg);
        }
    }

    public void sendMessage(SendMessage message) {
        try {
            myAppBot.execute(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendLocation(SendLocation location) {
        try {
            myAppBot.execute(location);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //LOCAL STATE
    public String getLocalState() {
        Optional<DataUserTg> dataUserByChatId = dataUserService.findDataUserByChatId(chatId);
        if (dataUserByChatId.isEmpty()) {
            return "пользователь не найден";
        }
        return dataUserByChatId.get().getLocaleState();
    }

    public String setLocalState(String newState) {
        Optional<DataUserTg> dataUserByChatId = dataUserService.findDataUserByChatId(chatId);
        if (dataUserByChatId.isPresent()) {
            DataUserTg dataUserTg = dataUserByChatId.get();
            dataUserTg.setLocaleState(newState);
            dataUserService.save(dataUserTg);
        }
        return newState;
    }

    public void printDateAndState() {
        Date date = new Date();
        // Вывод текущей даты и cостояний
        System.out.println(date + ":      GlobalState:  " + getGlobalState() + "   LocalState:  " + getLocalState());
    }

    public void replyMessage(String sendTEXT) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(sendTEXT);
        sendMessage(message);
    }

    public void sendAll(String sendTEXT) {
        SendMessage message = new SendMessage();
        List<Long> chatIdList = dataUserService.findAll();
        for (Long chatId : chatIdList) {
            message.setChatId(chatId.toString());
            message.setText(sendTEXT);
            sendMessage(message);
        }
    }


    public void sendAddress(double longitude,double latitude)  {
        SendLocation sendLocation = new SendLocation();
        sendLocation.setChatId(String.valueOf(chatId));
        sendLocation.setLongitude(longitude);
        sendLocation.setLatitude(latitude);
        sendLocation(sendLocation);
    }

    public void buildReplyKeyboard(String responseMessage, List<ReplyButton> buttonNames) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(responseMessage);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        buttonNames.forEach(e -> {
            KeyboardRow row = new KeyboardRow();

            row.add(new KeyboardButton(e.getReplyMesasge()));
            keyboardRowList.add(row);

        });

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        // Add it to the message
        message.setReplyMarkup(replyKeyboardMarkup);
        sendMessage(message);
    }

    public void buildInlineKeyboard(String replyMessage, List<InlineButton> inlineButtons) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(getChatId()));
        message.setText(replyMessage);
        // Create InlineKeyboardMarkup object
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        //Создаём клавиатуру (list of InlineKeyboardButton list)
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        // Создаём лист для кнопок
        List<InlineKeyboardButton> buttons = new ArrayList<InlineKeyboardButton>();


        inlineButtons.forEach((e) -> {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setUrl(e.getUrl());
            inlineKeyboardButton.setText(e.getText());
            buttons.add(inlineKeyboardButton);
        });

        keyboard.add(buttons);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage(message);

    }

}

//