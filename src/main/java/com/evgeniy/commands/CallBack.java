package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class CallBack implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {
        executionContext.replyMessage("В скором времени наш менеджер свяжеться с Вами");
        Optional<DataUserTg> dataUserByChatId = executionContext.getDataUserService().findDataUserByChatId(executionContext.getChatId());
        if(dataUserByChatId.isPresent()){
            String phone = dataUserByChatId.get().getPhone();
            executionContext.sendMessageToUserWithId("Перезвоните мне: "+phone,"1331264383");
        }
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Заказать обратный звонок");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.CALL_BACK;
    }
}
