package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.entity.ReplyButton;
import com.evgeniy.telegram.ExecutionContext;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;


@Component
@Data
public class Therapist implements Command {

    @Override
    public void doCommand(ExecutionContext executionContext) {
        LocalDate today = LocalDate.now();
        String inputMessage = executionContext.getInputText();
        String localState = executionContext.getLocalState();

        if (localState == null) {
            localState = executionContext.setLocalState("fistLocState");
        }

        switch (localState) {
            case "fistLocState":
                List<ReplyButton> replyButtons = List.of(new ReplyButton(today.toString()),
                        new ReplyButton(today.plusDays(1).toString()),
                        new ReplyButton(today.plusDays(2).toString()),
                        new ReplyButton(today.plusDays(3).toString()),
                        new ReplyButton(today.plusDays(4).toString()));
                executionContext.buildReplyKeyboard("На какое число вы хотите записаться?", replyButtons);
                executionContext.setLocalState("chose_data_to_appointment");
                break;

            case "chose_data_to_appointment":
                if (inputMessage.equals(today.toString())) {
                    executionContext.setLocalState("today");
                    List<String> strings = executionContext.freeTimeToAppointmentForDay(today,1L);
                    executionContext.buildReplyKeyboardWithStringList("Выберите время", strings);

                }
                if (inputMessage.equals(today.plusDays(1).toString())) {
                    executionContext.setLocalState("todayPlusDay");
                    List<String> strings = executionContext.freeTimeToAppointmentForDay(today,1L);
                    executionContext.buildReplyKeyboardWithStringList("Выберите время", strings);
                }
                if (inputMessage.equals(today.plusDays(2).toString())) {
                    executionContext.setLocalState("todayPlus2Day");
                    List<String> strings = executionContext.freeTimeToAppointmentForDay(today,1L);
                    executionContext.buildReplyKeyboardWithStringList("Выберите время", strings);
                }
                if (inputMessage.equals(today.plusDays(3).toString())) {
                    executionContext.setLocalState("todayPlus3Day");
                    List<String> strings = executionContext.freeTimeToAppointmentForDay(today,1L);
                    executionContext.buildReplyKeyboardWithStringList("Выберите время", strings);
                }
                if (inputMessage.equals(today.plusDays(4).toString())) {
                    executionContext.setLocalState("todayPlus4Day");
                    List<String> strings = executionContext.freeTimeToAppointmentForDay(today,1L);
                    executionContext.buildReplyKeyboardWithStringList("Выберите время", strings);
                }
                break;
            case "today":
                executionContext.createAppointmentToDoctor(today,inputMessage,"1");
                executionContext.setLocalState(null);
                executionContext.setGlobalState(null);
                break;
            case "todayPlusDay":
                executionContext.createAppointmentToDoctor(today.plusDays(1),inputMessage,"1");
                executionContext.setLocalState(null);
                executionContext.setGlobalState(null);
                break;
            case "todayPlus2Day":
                executionContext.createAppointmentToDoctor(today.plusDays(2),inputMessage,"1");
                executionContext.setLocalState(null);
                executionContext.setGlobalState(null);
                break;
            case "todayPlus3Day":
                executionContext.createAppointmentToDoctor(today.plusDays(3),inputMessage,"1");
                executionContext.setLocalState(null);
                executionContext.setGlobalState(null);
                break;
            case "todayPlus4Day":
                executionContext.createAppointmentToDoctor(today.plusDays(4),inputMessage,"1");
                executionContext.setLocalState(null);
                executionContext.setGlobalState(null);
                break;
        }
    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Терапевт");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.THERAPIST;
    }
}