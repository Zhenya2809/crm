package com.evgeniy.telegram;

import com.evgeniy.commands.Command;
import com.evgeniy.commands.Start;
import com.evgeniy.entity.DataUserTg;
import com.evgeniy.service.DataUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class MyAppBot extends TelegramLongPollingBot {
        @Autowired
        private DataUserService dataUserService;
        public static final List<Command> commands = new ArrayList<>();

    static {
        commands.add(new Start());
    }
    public HashMap<Long, DataUserTg> user = new HashMap<>();

    @Override
    public String getBotUsername() {
        return "InWarHelper";
    }

    @Override
    public String getBotToken() {
        return "5268155371:AAGEdY8Rzw4kEp0s4-nL51D-oHQp2ULRAds";
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

            if (!CheckLoggin(chatId)) {
                dataUserService.createUser(chatId, firstName, lastName);
            }

            user.computeIfAbsent(chatId, a -> new DataUserTg(chatId, firstName, lastName));
            Command command = null;
            for (Command choseCommand : commands) {
                if (choseCommand.shouldRunOnText(inputText) || (choseCommand.getGlobalState()
                        != null && user.get(chatId).GlobalState == choseCommand.getGlobalState())) {
                    command = choseCommand;
                    user.get(chatId).GlobalState = choseCommand.getGlobalState();
                }
            }

            ExecutionContext context = new ExecutionContext();
            context.setFirstName(firstName);
            context.setLastName(lastName);
            context.setChatId(chatId);
            context.setInputText(inputText);
            context.setMyAppBot(this);
            context.setUpdate(update);
            context.setDataUserService(dataUserService);



            if (command != null) {
                log.info("start command: " + command.getClass().getSimpleName());
                command.doCommand(context);
            }
//            context.PrintDateAndState();

        } catch (Exception e) {
            log.error("error", e);

            e.printStackTrace();

        } finally {
            MDC.clear();
        }
    }

    public void replyMessage(String sendTEXT, Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(sendTEXT);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("error", e);
        }
    }

    public boolean CheckLoggin(Long chatId) {
        Optional<DataUserTg> dataUserByChatId = dataUserService.findDataUserByChatId(chatId);
        return dataUserByChatId.isPresent();
    }

    @PostConstruct
    public void register() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }
}