package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.service.DataUserService;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class StartChattingWithBot implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {
        String inputMessage = executionContext.getInputText();
        String localState = executionContext.getLocalState();
        DataUserService dataUserService = executionContext.getDataUserService();
        Optional<DataUserTg> dataUserByChatId = dataUserService.findDataUserByChatId(executionContext.getChatId());

        if ((dataUserByChatId.get().getEmail() != null) && (dataUserByChatId.get().getPhone() != null)) {
            localState = executionContext.setLocalState("Юзер зарегистрирован");
        }
        if ((localState == null) && (dataUserByChatId.get().getEmail() == null) && (dataUserByChatId.get().getPhone() == null)) {
            localState = executionContext.setLocalState("Регистрация");
        }
        if (localState == null) {
            localState = executionContext.setLocalState("Юзер зарегистрирован");
        }

        switch (localState) {

            case "Юзер зарегистрирован":
                List<ReplyButton> replyButtonList = List.of(new ReplyButton("О нас"),
                        new ReplyButton("Специалисты"),
                        new ReplyButton("Услуги"),
                        new ReplyButton("Косметика"),
                        new ReplyButton("Наш адрес"));
                executionContext.buildReplyKeyboard("О, как так? Тогда нам есть о чем поговорить! \n" +
                        "так хочеться рассказать тебе о нас", replyButtonList);
                executionContext.setLocalState(null);
                executionContext.setGlobalState(null);
                break;
            case "Регистрация":
                executionContext.replyMessage("Для того что бы продолжить, мне нужно с тобой познакомиться \n" +
                        "Введите email");
                executionContext.setLocalState("TypeEmail");
                break;
            case "TypeEmail":
                DataUserTg user = executionContext.getDataUserService().findDataUserByChatId(executionContext.getChatId()).get();
                user.setEmail(inputMessage);
                executionContext.getDataUserService().save(user);
                executionContext.getContactKeyboard();
                executionContext.setLocalState("TypePhone");
                break;
            case "TypePhone":
                executionContext.replyMessage("Cпасибо");
                replyButtonList = List.of(new ReplyButton("О нас"),
                        new ReplyButton("Специалисты"),
                        new ReplyButton("Услуги"),
                        new ReplyButton("Косметика"),
                        new ReplyButton("Наш адрес"));

                executionContext.buildReplyKeyboard("Нам есть о чем поговорить! \n" +
                        "так хочеться рассказать тебе о нас", replyButtonList);
                executionContext.setLocalState(null);
                executionContext.setGlobalState(null);
                break;
        }
    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Начнем \uD83D\uDE09");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.START_BOT_CHATTING;
    }
}