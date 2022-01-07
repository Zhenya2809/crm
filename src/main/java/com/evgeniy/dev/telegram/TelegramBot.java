package com.evgeniy.dev.telegram;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBot extends TelegramLongPollingBot{
    @Override
    public String getBotUsername() {
        return "crmBot";
    }

    @Override
    public String getBotToken() {
        return "5019798670:AAHRFdhwhS8_p7xM8Xig_IsWAB2m3FjEiS8";
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
