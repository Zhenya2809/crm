package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Address implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) throws TelegramApiException {
        executionContext.setGlobalState(DataUserTg.botstate.ADDRESS);

executionContext.replyMessage("Найти нас очень просто!\n" +
        "Киев, ул. Вацлава Гавела 9а \n" +
        "или позвонить нам " +
        "\uD83D\uDCDE +38(077)-777-77-77");
        executionContext.sendAddress(30.425590655889714,50.44593677173056);
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Наш адрес");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.ADDRESS;
    }
}
