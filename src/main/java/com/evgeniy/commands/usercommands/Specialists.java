package com.evgeniy.commands.usercommands;

import com.evgeniy.commands.Command;
import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Specialists implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {
        executionContext.setGlobalState(DataUserTg.botstate.SPECIALISTS);
        List<ReplyButton> replyButtonList = List.of(new ReplyButton("Доктора"),
                                                    new ReplyButton("і тут ще хтось"),
                                                    new ReplyButton("Главное меню"));

        executionContext.buildReplyKeyboard(executionContext.getFirstName() + ", рада представить тебе нашу команду профессионалов. \n" +
                "Качественный подбор персонала, помог собрать лучших врачей - специалистов.", replyButtonList);


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