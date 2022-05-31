package com.evgeniy.commands.usercommands;

import com.evgeniy.commands.Command;
import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.InlineButton;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AboutUs implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {

        List<InlineButton> inlineButtons = List.of(new InlineButton("Instagram", "https://instagram.com"), new InlineButton("Facebook", "https://facebook.com"));
        executionContext.buildInlineKeyboard("Возможно тебя заинтересует одна из наших соц. сетей?", inlineButtons);

        List<ReplyButton> replyButtonList = List.of(
                new ReplyButton("Наш адрес"),
                new ReplyButton("Главное меню"));

        executionContext.buildReplyKeyboard("CLINIC_NAME — это частная клиника в Киеве для всей семьи.", replyButtonList);
        executionContext.replyMessage("""
                Мы позаботимся как о новорожденном ребенке, так и о людях почтенного возраста.
                Мы предоставляем медицинские услуги с выездом на дом, в клинике и онлайн,\s
                чтобы всегда держать под контролем ваше хорошее самочувствие.""");

    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("О нас");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.ABOUT;
    }
}