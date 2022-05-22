package com.evgeniy.telegram;

import com.evgeniy.commands.*;
import com.evgeniy.entity.Kozelec;
import com.evgeniy.entity.DataUserTg;

import com.evgeniy.repository.KozelecRepository;
import com.evgeniy.service.AppointmentService;
import com.evgeniy.service.DataUserService;
import com.evgeniy.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.util.*;


@Slf4j
@Component
public class MyAppBot extends TelegramLongPollingBot {
    @Autowired
    private DataUserService dataUserService;
    @Autowired
    private KozelecRepository kozelecRepository;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AppointmentService appointmentService;

    private static final Integer CACHETIME = 1;
    private static final String THUMB_URL = "https://anga.ua/files/anga/reg_images/kozeleth5.jpg";
    @Autowired
    public List<Command> commands;

    public HashMap<Long, DataUserTg> user = new HashMap<>();

    @Override
    public String getBotUsername() {
        return "InWarHelper";
    }

    @Override
    public String getBotToken() {
        return "5220644891:AAEOsFotO-rlhBHyCBf7pZEmnseZP7x8S5U";
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {

            if (update.getMessage().hasContact()) {
                String phoneNumber = update.getMessage().getContact().getPhoneNumber();
                registerContactNumber(update,phoneNumber);
            }
            if (update.hasInlineQuery()) {
                handleIncomingInlineQuery(update.getInlineQuery());
                System.out.println(" update query= " + update.getInlineQuery().getQuery());
            } else if (update.hasMessage()) {

                Long chatId = update.getMessage().getChatId();
                String firstName = update.getMessage().getChat().getFirstName();
                String lastName = update.getMessage().getChat().getLastName();
                String inputText = update.getMessage().getText();

                MDC.put("firstName", firstName);
                MDC.put("lastName", lastName);

                if (!CheckLoggin(chatId)) {
                    dataUserService.createUser(chatId, firstName, lastName);
                }

                user.computeIfAbsent(chatId, a -> new DataUserTg(chatId, firstName, lastName));
                Command command = null;
                for (Command choseCommand : commands) {
                    if (inputText != null) {
                        if (choseCommand.shouldRunOnText(inputText) || (choseCommand.getGlobalState()
                                != null && user.get(chatId).globalState == choseCommand.getGlobalState())) {
                            command = choseCommand;
                            user.get(chatId).globalState = choseCommand.getGlobalState();
                        }
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
                context.setDoctorService(doctorService);
                context.setAppointmentService(appointmentService);

                if (command != null) {
                    log.info("start command: " + command.getClass().getSimpleName());
                    command.doCommand(context);
                }
            }
        } catch (Exception e) {
            log.error("error", e);

            e.printStackTrace();

        } finally {
            MDC.clear();
        }
    }

    public boolean CheckLoggin(Long chatId) {
        Optional<DataUserTg> dataUserByChatId = dataUserService.findDataUserByChatId(chatId);
        return dataUserByChatId.isPresent();
    }

    private void handleIncomingInlineQuery(InlineQuery inlineQuery) {
        String query = inlineQuery.getQuery();
        System.out.println("Searching: " + query);
        try {
            execute(convertResultsToResponse(inlineQuery));
        } catch (Throwable e) {
            log.error(e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
    }

    private AnswerInlineQuery convertResultsToResponse(InlineQuery inlineQuery) {
        AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
        answerInlineQuery.setInlineQueryId(inlineQuery.getId());
        System.out.println("answerID=" + inlineQuery.getId());
        answerInlineQuery.setCacheTime(CACHETIME);
        answerInlineQuery.setIsPersonal(true);
        answerInlineQuery.setResults(convertResults(inlineQuery.getQuery()));
        return answerInlineQuery;
    }

    private List<InlineQueryResult> convertResults(String query) {

        String[] array = query.split(",");
        String region = query;
        String city = "";
        String street = "";
        String number = "";

        if (array.length == 2) {
            region = array[0];
            city = array[1];
        }
        if (array.length == 3) {
            region = array[0];
            city = array[1];
            street = array[2];
        }
        if (array.length == 4) {
            region = array[0];
            city = array[1];
            street = array[2];
            number = array[3];
        }

        List<Kozelec> kozeletsDistrictsList = kozelecRepository.searchAddress(region.toLowerCase(Locale.ROOT), city.toLowerCase(Locale.ROOT), street.toLowerCase(Locale.ROOT), number.toLowerCase(Locale.ROOT)).stream().toList();

        List<InlineQueryResult> results = new ArrayList<>();

        for (Kozelec kozelec : kozeletsDistrictsList) {

            InlineQueryResultArticle article = new InlineQueryResultArticle();
            InputTextMessageContent messageContent = new InputTextMessageContent();

            messageContent.setDisableWebPagePreview(true);
            messageContent.setParseMode(ParseMode.MARKDOWN);
            messageContent.setMessageText(kozelec.getStreet() + kozelec.getNumber());
            article.setInputMessageContent(messageContent);
            article.setId(kozelec.getId().toString());
            article.setTitle(kozelec.getRegion() + " обл.  " + kozelec.getTitle());
            article.setDescription(kozelec.getStreet() + " , " + kozelec.getNumber());
            article.setThumbUrl(THUMB_URL);
            results.add(article);

        }

        return results;
    }

    public void registerContactNumber(Update update, String phoneNumber) {
        try {
            System.out.println(phoneNumber);
            DataUserTg user = dataUserService.findDataUserByChatId(update.getMessage().getChatId()).get();
            user.setPhone(phoneNumber);
            dataUserService.save(user);

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText("Регистрация успешна! Спасибо!");
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboardRowList = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton();
            keyboardButton.setText("Главное меню");
            row.add(keyboardButton);
            keyboardRowList.add(row);
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);
            replyKeyboardMarkup.setKeyboard(keyboardRowList);
            message.setReplyMarkup(replyKeyboardMarkup);
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct

    public void register() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }


}