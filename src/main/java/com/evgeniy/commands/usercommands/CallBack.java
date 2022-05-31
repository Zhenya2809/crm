package com.evgeniy.commands.usercommands;

import com.evgeniy.commands.Command;
import com.evgeniy.entity.DataUserTg;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class CallBack implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {
        executionContext.replyMessage("В скором времени наш менеджер свяжеться с Вами");
        Optional<DataUserTg> dataUserByChatId = executionContext.getDataUserService().findDataUserByChatId(executionContext.getChatId());
        if(dataUserByChatId.isPresent()){
            String phone = dataUserByChatId.get().getPhone();
            Long administratorId = executionContext.getInfoDataService().getAdministratorId();
            executionContext.sendMessageToUserWithId("Перезвоните мне: "+phone,administratorId +executionContext.getUser().getFirstName()+" "+executionContext.getUser().getLastName());
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
