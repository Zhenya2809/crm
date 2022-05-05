package com.evgeniy.telegram;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.service.DataUserService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Slf4j
@Component
public class ExecutionContext {
    private MyAppBot myAppBot;
    public DataUserTg.botstate GlobalState;
    private Long chatId;
    private String firstName;
    private String lastName;
    private String inputText;
    private Update update;
    private DataUserService dataUserService;

    public void setGlobalState(DataUserTg.botstate newState) {
        Optional<DataUserTg> dataUserByChatId = dataUserService.findDataUserByChatId(chatId);
        if (dataUserByChatId.isPresent()) {
            DataUserTg userTg = dataUserByChatId.get();
            userTg.setGlobalState(newState);
            dataUserService.save(userTg);
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

    public void sendKeyboardMainMenu() {
        MyAppBot myAppBot = new MyAppBot();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Меню:");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        KeyboardRow row5 = new KeyboardRow();
        KeyboardRow row6 = new KeyboardRow();
        KeyboardRow row7 = new KeyboardRow();
        KeyboardRow row8 = new KeyboardRow();

        row1.add(new KeyboardButton("КУДА СООБЩАТЬ, ЕСЛИ ЗАМЕТИЛИ РОССИЙСКИХ ОККУПАНТОВ ИЛИ ИХ ТЕХНИКУ \uD83C\uDDFA\uD83C\uDDE6"));
        row2.add(new KeyboardButton("Техника русни \uD83C\uDFF3"));
        row3.add(new KeyboardButton("Список полезных ботов"));
        row4.add(new KeyboardButton("Информация"));
        row4.add(new KeyboardButton("Помощь военным"));
        row5.add(new KeyboardButton("Статут ЗСУ"));
        row5.add(new KeyboardButton("Огнестрельное оружие"));

//        row3.add(new KeyboardButton("тест2"));
//        row3.add(new KeyboardButton("тест3"));
        row6.add(new KeyboardButton("Главное меню"));
//        row5.add(new KeyboardButton("тест5"));


        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        keyboard.add(row5);
        keyboard.add(row6);
        keyboard.add(row7);
        keyboard.add(row8);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(replyKeyboardMarkup);

        try {
            // Send the message
            myAppBot.execute(message);
        } catch (TelegramApiException e) {
            log.error("error", e);
            e.printStackTrace();
        }
    }

    public void setMyWorkBot(MyAppBot myAppBot) {
        this.myAppBot = myAppBot;
    }

    public void PrintDateAndState() {
        Date date = new Date();
        // Вывод текущей даты и cостояний
        System.out.println(date + ":      GlobalState:  " + getGlobalState() + "   LocalState:  " + getLocalState());
    }

    public void replyMessage(String sendTEXT) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(sendTEXT);
        try {
            myAppBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("error", e);
        }

    }

    public void sendAll(String sendTEXT) {
        SendMessage message = new SendMessage();
        List<Long> chatIdList = dataUserService.findAll();
        for (Long chatId : chatIdList) {
            message.setChatId(chatId.toString());
            message.setText(sendTEXT);
            try {
                myAppBot.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
                log.error("error", e);
            }

        }
    }
}

//