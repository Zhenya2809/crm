package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.InlineButton;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AboutClinicText implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {
        executionContext.setGlobalState(DataUserTg.botstate.ABOUT_CLINIC_TEXT);

        List<InlineButton> inlineButtons= List.of(new InlineButton("Instagram","https://instagram.com"),new InlineButton("Facebook","https://facebook.com"));
        executionContext.buildInlineKeyboard("Возможно тебя заинтересует одна из наших соц. сетей?",inlineButtons);

        List<ReplyButton> replyButtonList = List.of(new ReplyButton("Ок, расскажи о CLINIC_NAME"),new ReplyButton("Главное меню"));

        executionContext.buildReplyKeyboard("Так хочеться рассказать тебе о нас", replyButtonList);

    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Ок, расскажи о CLINIC_NAME");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.ABOUT_CLINIC_TEXT;
    }
}