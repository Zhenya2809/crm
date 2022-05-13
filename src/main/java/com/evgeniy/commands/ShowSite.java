package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.InlineButton;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class ShowSite implements Command {

    @Override
    public void doCommand(ExecutionContext executionContext) {
        executionContext.setGlobalState(DataUserTg.botstate.SHOW_SITE);
        List<InlineButton>  inlineButtons= List.of(new InlineButton("Наш сайт","http://95.216.146.138:8080/"));
        executionContext.buildInlineKeyboard("Перейдите на наш сайт",inlineButtons);
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);
    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Покажи свой сайт \uD83C\uDF10");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.SHOW_SITE;
    }
}