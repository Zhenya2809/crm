package com.evgeniy.telegram;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.service.DataUserService;
import com.evgeniy.service.DoctorService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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




    public void sendKeyboardChoseYesOrNo() {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Вы были у нас раньше?");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row1.add(new KeyboardButton("Да"));
        row2.add(new KeyboardButton("Нет"));
        keyboard.add(row1);
        keyboard.add(row2);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(replyKeyboardMarkup);

        sendMessage(message);
    }

    public void sendKeyboardIfChoseYes() {
        MyAppBot myAppBot = new MyAppBot();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Какая услуга тебя интересует?");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row1.add(new KeyboardButton("Специалисты"));
        row2.add(new KeyboardButton("Главное меню"));
        keyboard.add(row1);
        keyboard.add(row2);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(replyKeyboardMarkup);

        sendMessage(message);
    }

    public void sendKeyboardAbout() {
        MyAppBot myAppBot = new MyAppBot();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Так хочеться рассказать тебе о нас");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row1.add(new KeyboardButton("Персонал"));
        row2.add(new KeyboardButton("Главное меню"));
        keyboard.add(row1);
        keyboard.add(row2);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(replyKeyboardMarkup);

        sendMessage(message);
    }

    public void sendKeyboardSpecialists() {
        MyAppBot myAppBot = new MyAppBot();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Какой наш специалист тебя интересует?");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        row1.add(new KeyboardButton("Доктора"));
        row2.add(new KeyboardButton("Косметологи"));
        row3.add(new KeyboardButton("Доктора"));
        row4.add(new KeyboardButton("Косметологи"));
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(replyKeyboardMarkup);

        sendMessage(message);
    }

    public void sendInlineKeyboardAbout() {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
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

        sendMessage(message);
    }

    public void sendInlineKeyboardShowSite() {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Перейдите на наш сайт");
        // Create InlineKeyboardMarkup object
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        //Создаём клавиатуру (list of InlineKeyboardButton list)
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        // Создаём лист для кнопок
        List<InlineKeyboardButton> Buttons = new ArrayList<InlineKeyboardButton>();
        // Инициализируем кнопку и текст к кнопке
        InlineKeyboardButton news = new InlineKeyboardButton("Наш сайт");
        // Нужно выбрать одно из опциональных полей и можна добавить его с помощью set method
        news.setUrl("http://95.216.146.138:8080/");
        // Добавляем кнопку в лист
        Buttons.add(news);
        // Инициализируем кнопку и текст к кнопке
        keyboard.add(Buttons);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(inlineKeyboardMarkup);

        sendMessage(message);
    }

    public void sendKeyboardMainMenu() {
        MyAppBot myAppBot = new MyAppBot();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("О, как так? Тогда нам есть о чем поговорить! \n" +
                "так хочеться рассказать тебе о нас");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
//        KeyboardRow row5 = new KeyboardRow();

        row1.add(new KeyboardButton("О нас"));
        row2.add(new KeyboardButton("Специалисты"));
        row3.add(new KeyboardButton("Услуги"));
        row4.add(new KeyboardButton("Наш адрес"));
//        row5.add(new KeyboardButton("Главное меню"));

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
//        keyboard.add(row5);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(replyKeyboardMarkup);

        sendMessage(message);
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


    public void keyboad(String responseMassage, List<String> buttonNames) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(responseMassage);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        buttonNames.forEach(e -> {
            KeyboardRow row = new KeyboardRow();
            row.add(new KeyboardButton(e));
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


}

//