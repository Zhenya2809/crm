package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Specialists implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext)  {
        executionContext.setGlobalState(DataUserTg.botstate.SPECIALISTS);
        List<ReplyButton> replyButtonList = List.of(new ReplyButton("Доктора"),
                new ReplyButton("Косметологи"),
                new ReplyButton("ще якісь хуї"),
                new ReplyButton("і тут ще хтось"));

        executionContext.buildReplyKeyboard("Какой наш специалист тебя интересует?", replyButtonList);


        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);
    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Специалисты");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.SPECIALISTS;
    }
}