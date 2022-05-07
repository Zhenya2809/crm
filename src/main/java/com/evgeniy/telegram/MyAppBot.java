package com.evgeniy.telegram;

import com.evgeniy.commands.Command;
import com.evgeniy.commands.Start;
import com.evgeniy.entity.AnswerQuery;
import com.evgeniy.entity.DataUserTg;
import com.evgeniy.service.AnswerQueryService;
import com.evgeniy.service.DataUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
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
    private AnswerQueryService answerQueryService;
    @Autowired
    private DataUserService dataUserService;

    private static final Integer CACHETIME = -1;

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
        return "5220644891:AAEOsFotO-rlhBHyCBf7pZEmnseZP7x8S5U";
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
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

    private  AnswerInlineQuery convertResultsToResponse(InlineQuery inlineQuery) {
        AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
        answerInlineQuery.setInlineQueryId(inlineQuery.getId());
        System.out.println("answerID="+inlineQuery.getId());
        answerInlineQuery.setCacheTime(CACHETIME);
        answerInlineQuery.setIsPersonal(true);
        answerInlineQuery.setResults(convertResults(inlineQuery.getQuery()));
        return answerInlineQuery;
    }

    private  List<InlineQueryResult> convertResults(String query) {
        List<AnswerQuery> answerQueryList = answerQueryService.findByTitleContains(query);


        List<InlineQueryResult> results = new ArrayList<>();
        for (int i = 0; i < answerQueryList.size(); i++) {
            InlineQueryResultArticle article = new InlineQueryResultArticle();
            InputTextMessageContent messageContent  = new InputTextMessageContent();
            messageContent.setDisableWebPagePreview(true);
            messageContent.setParseMode(ParseMode.MARKDOWN);
            messageContent.setMessageText(answerQueryList.get(i).getDescription());
            article.setInputMessageContent(messageContent);
            article.setId(answerQueryList.get(i).getId().toString());
            System.out.println("articleID="+answerQueryList.get(i).getId().toString());
            article.setTitle(answerQueryList.get(i).getTitle());
            article.setDescription(answerQueryList.get(i).getDescription());
            article.setThumbUrl(answerQueryList.get(i).getThumbUrl());
            results.add(article);
        }

       return results;
    }

    @PostConstruct
    public void register() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }


}