package com.evgeniy.commands;

import com.evgeniy.commands.localState.LocalStateForAppointment;
import com.evgeniy.entity.*;
import com.evgeniy.telegram.ExecutionContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
public class AppointmentToDoctor implements Command {
    @Override
    public void doCommand(ExecutionContext executionContext) {
        try {
            LocalDate today = LocalDate.now();
            String inputMessage = executionContext.getInputText();
            String localState = executionContext.getLocalState();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();

            if (localState == null) {
                LocalStateForAppointment localStateForAppointment = new LocalStateForAppointment(null, "sendDoctorSpeciality",null);
                executionContext.setLocalState(objectMapper.writeValueAsString(localStateForAppointment));
            }
            LocalStateForAppointment localStateForAppointment = objectMapper.readValue(executionContext.getLocalState(), LocalStateForAppointment.class);
            String step = localStateForAppointment.getStep();
            System.out.println("step=" + step);
            Long docId = localStateForAppointment.getDoctorId();

            switch (step) {
                case "sendDoctorSpeciality":
                    List<Doctor> all = executionContext.getDoctorService().findAll();
                    List<InlineButton> inlineButtons = all.stream().map(doctor -> {
                        String speciality = doctor.getSpeciality();
                        Long id = doctor.getId();
                        return new InlineButton(speciality, "http://95.216.146.138:8080/clinic/" + id);
                    }).toList();
                    List<ReplyButton> replyButtons = all.stream().map(e -> new ReplyButton(e.getSpeciality())).toList();
                    executionContext.buildInlineKeyboard("перейти на сайт для записи", inlineButtons);
                    executionContext.buildReplyKeyboard("Продолжить через телеграмм", replyButtons);
                    localStateForAppointment.setStep("chose_doctor");
                    executionContext.setLocalState(objectMapper.writeValueAsString(localStateForAppointment));

                    break;
                case "chose_doctor":
                    List<Doctor> doctorsBySpeсiality = executionContext.getDoctorService().findDoctorsBySperiality(inputMessage);
                    List<ReplyButton> doctorsFIOListForButton = doctorsBySpeсiality.stream().map(e -> new ReplyButton(e.getFio())).toList();
                    executionContext.buildReplyKeyboard("Выберите доктора", doctorsFIOListForButton);
                    localStateForAppointment.setStep("chose_id");
                    executionContext.setLocalState(objectMapper.writeValueAsString(localStateForAppointment));
                    break;
                case "chose_id":
                    localStateForAppointment.setDoctorId(executionContext.getDoctorService().findDoctorByFio(inputMessage).getId());
                    replyButtons = List.of(new ReplyButton(today.toString()),
                            new ReplyButton(today.plusDays(1).toString()),
                            new ReplyButton(today.plusDays(2).toString()),
                            new ReplyButton(today.plusDays(3).toString()),
                            new ReplyButton(today.plusDays(4).toString()));
                    executionContext.buildReplyKeyboard("На какое число вы хотите записаться?", replyButtons);
                    localStateForAppointment.setStep("chose_data_to_appointment");
                    executionContext.setLocalState(objectMapper.writeValueAsString(localStateForAppointment));
                    break;

                case "chose_data_to_appointment":
                    localStateForAppointment.setDate(LocalDate.parse(inputMessage));
                    extracted(executionContext, today, docId);
                    localStateForAppointment.setStep("choseTime");
                    executionContext.setLocalState(objectMapper.writeValueAsString(localStateForAppointment));
                    break;
                case "choseTime":
                    executionContext.createAppointmentToDoctor(localStateForAppointment.getDate(), inputMessage, String.valueOf(docId));
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Записаться к доктору");
    }

    private void extracted(ExecutionContext executionContext, LocalDate today, Long docId) {
        List<String> strings = executionContext.freeTimeToAppointmentForDay(today, docId);
        executionContext.buildReplyKeyboardWithStringList("Выберите время", strings);
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.APPOINTMENT_TO_DOCTOR;
    }
}