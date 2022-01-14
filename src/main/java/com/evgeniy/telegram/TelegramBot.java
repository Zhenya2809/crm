package com.evgeniy.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger log
            = LoggerFactory.getLogger(TelegramBot.class);


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

        try {
            Long chatId = update.getMessage().getChatId();
            String firstName = update.getMessage().getChat().getFirstName();
            String lastName = update.getMessage().getChat().getLastName();
            String inputText = update.getMessage().getText();

            MDC.put("firstName", firstName);
            MDC.put("lastName", lastName);
            if (!update.hasMessage()) {
                return;
            }


        } catch (Exception e) {
            log.error("error", e);

            e.printStackTrace();

        } finally {
            MDC.clear();
        }
    }
}
