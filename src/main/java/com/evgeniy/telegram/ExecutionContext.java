package com.evgeniy.telegram;

import com.evgeniy.entity.*;
import com.evgeniy.service.AppointmentService;
import com.evgeniy.service.DataUserService;
import com.evgeniy.service.DoctorService;
import com.evgeniy.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.LocalDate;
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
    private AppointmentService appointmentService;
    private UserService userService;

    public void setGlobalState(DataUserTg.botstate newState) {
        Optional<DataUserTg> dataUserByChatId = dataUserService.findDataUserByChatId(chatId);
        if (dataUserByChatId.isPresent()) {
            DataUserTg userTg = dataUserByChatId.get();
            userTg.setGlobalState(newState);
            dataUserService.save(userTg);
        }
    }

    public void execute(SendMessage message) {
        try {
            myAppBot.execute(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(SendLocation location) {
        try {
            myAppBot.execute(location);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(SendPhoto sendPhoto) {
        try {
            myAppBot.execute(sendPhoto);
        } catch (Exception e) {
            throw new RuntimeException();
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

    public String printDateAndState() {
        Date date = new Date();
        // Вывод текущей даты и cостояний
        return date + ":      GlobalState:  " + getGlobalState() + "   LocalState:  " + getLocalState();
    }

    public void replyMessage(String sendTEXT) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(sendTEXT);
        execute(message);
    }

    public void sendAll(String sendTEXT) {
        SendMessage message = new SendMessage();
        List<Long> chatIdList = dataUserService.findAll();
        for (Long chatId : chatIdList) {
            message.setChatId(chatId.toString());
            message.setText(sendTEXT);
            execute(message);
        }
    }


    public void sendAddress(double longitude, double latitude) {
        SendLocation sendLocation = new SendLocation();
        sendLocation.setChatId(String.valueOf(chatId));
        sendLocation.setLongitude(longitude);
        sendLocation.setLatitude(latitude);
        execute(sendLocation);
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
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        // Add it to the message
        message.setReplyMarkup(replyKeyboardMarkup);
        execute(message);
    }

    public void buildReplyKeyboardWithStringList(String responseMessage, List<String> buttonNames) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(responseMessage);

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
        execute(message);
    }
    public void getContactKeyboard() {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Поделись своим номером телефона:");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowList = new ArrayList<>();


            KeyboardRow row = new KeyboardRow();
            KeyboardButton keyboardButton=new KeyboardButton();
            keyboardButton.setText("поделиться номером телефона");
            keyboardButton.setRequestContact(true);
//            keyboardButton.setRequestLocation(true);
            row.add(keyboardButton);
            keyboardRowList.add(row);


        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        // Add it to the message
        message.setReplyMarkup(replyKeyboardMarkup);
        execute(message);

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
        execute(message);

    }

    public void replyImage(String photoLink) {

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());
        InputFile inputFile = new InputFile();
        inputFile.setMedia(photoLink);
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setParseMode("*bold \\*text*");
        execute(sendPhoto);
    }

    public List<String> freeTimeToAppointmentForDay(LocalDate day, Long docId) {
        List<String> timeList = new ArrayList<>();
        timeList.add("9:00");
        timeList.add("10:00");
        timeList.add("11:00");
        timeList.add("12:00");
        timeList.add("13:00");
        timeList.add("14:00");
        timeList.add("15:00");
        timeList.add("16:00");
        timeList.add("17:00");
        timeList.add("18:00");
        HashMap<String, List<String>> dateAndTimeMap = appointmentService.findAllAvailableTimeByDoctorId(docId);
        LocalDate today = LocalDate.now();
        ArrayList<DaySchedule> daySchedules = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            LocalDate localDate = today.plusDays(i);
            DaySchedule daySchedule = new DaySchedule();
            String key = localDate.toString();
            daySchedule.setDate(key);
            if (dateAndTimeMap.get(key) != null) {
                daySchedule.setAvailable(new HashSet<>(dateAndTimeMap.get(key)));
            } else {
                daySchedule.setAvailable(new HashSet<>());
            }
            daySchedules.add(daySchedule);

        }
        List<String> listToday = new ArrayList<>();

        daySchedules.forEach(e -> {
            String date = e.getDate();
            HashSet<String> available = e.getAvailable();
            if (date.equals(day.toString())) {
                listToday.addAll(available);
            }
        });

        timeList.removeAll(listToday);
        return timeList;
    }

    public void createAppointmentToDoctor(LocalDate day, String time, String docId) {

        String email = dataUserService.findDataUserByChatId(getChatId()).get().getEmail();
        appointmentService.createAppointmentTDoctors(email, day.toString(), time, docId);
        replyMessage(getFirstName() + " ты записан " + day + " на " + time + "\n с нетерпение ждём тебя");
        List<String> butonsNameList = List.of("Наш адрес", "Услуги", "Специалисты", "Контакты", "Главное меню");
        buildReplyKeyboardWithStringList("Возможно я готов помочь тебе ещё?", butonsNameList);
        setGlobalState(null);
        setLocalState(null);
    }

}

//