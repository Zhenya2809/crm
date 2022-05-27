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
        List<ReplyButton> replyButtonList = List.of(new ReplyButton("Главное меню"),new ReplyButton("Наш адрес"));

        executionContext.buildReplyKeyboard("CLINIC_NAME — это частная клиника в Киеве для всей семьи.", replyButtonList);
        executionContext.replyMessage("Мы позаботимся как о новорожденном ребенке, так и о людях почтенного возраста.\n" +
                " Мы предоставляем медицинские услуги с выездом на дом, в клинике и онлайн, \n чтобы всегда держать под контролем ваше хорошее самочувствие.");

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