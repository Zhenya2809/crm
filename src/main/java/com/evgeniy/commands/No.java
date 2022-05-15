package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.service.DataUserService;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendContact;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class No implements Command {


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
                executionContext.replyMessage("Регистрация:");
                executionContext.replyMessage("Введите email");
                executionContext.setLocalState("TypeEmail");
                break;
            case "TypeEmail":
                DataUserTg user = executionContext.getDataUserService().findDataUserByChatId(executionContext.getChatId()).get();
                user.setEmail(inputMessage);
                executionContext.getDataUserService().save(user);
                executionContext.replyMessage("Введите номер телефона");
                executionContext.setLocalState("TypePhone");
                break;
            case "TypePhone":
                System.out.println("telephone= " + inputMessage);

                if (inputMessage.matches("([0-9]{9,11})")) {
                    user = executionContext.getDataUserService().findDataUserByChatId(executionContext.getChatId()).get();
                    user.setPhone(inputMessage);
                    executionContext.getDataUserService().save(user);
                    executionContext.replyMessage("Cпасибо");
                    replyButtonList = List.of(new ReplyButton("О нас"),
                            new ReplyButton("Специалисты"),
                            new ReplyButton("Услуги"),
                            new ReplyButton("Косметика"),
                            new ReplyButton("Наш адрес"));

                    executionContext.buildReplyKeyboard("О, как так? Тогда нам есть о чем поговорить! \n" +
                            "так хочеться рассказать тебе о нас", replyButtonList);
                    executionContext.setLocalState(null);
                    executionContext.setGlobalState(null);

                } else {
                    executionContext.setLocalState("TypePhone");
                    executionContext.sendErrorMessage("введите телефон ещё раз");
                }
                break;
        }
    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Нет");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.NO;
    }
}