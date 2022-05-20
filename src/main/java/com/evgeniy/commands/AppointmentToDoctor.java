package com.evgeniy.commands;

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

            if (localState == null) {
                LocalStateForAppointment localStateForAppointment = new LocalStateForAppointment(null, "sendDoctorSpeciality");
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
                    List<ReplyButton> replyButtons1 = doctorsBySpeсiality.stream().map(e -> new ReplyButton(e.getFio())).toList();
                    executionContext.buildReplyKeyboard("Выберите доктора", replyButtons1);
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
                    if (inputMessage.equals(today.toString())) {
                        extracted(executionContext, today, docId);
                        localStateForAppointment.setStep("today");
                        executionContext.setLocalState(objectMapper.writeValueAsString(localStateForAppointment));
                    }
                    if (inputMessage.equals(today.plusDays(1).toString())) {
                        extracted(executionContext, today, docId);
                        localStateForAppointment.setStep("todayPlusDay");
                        executionContext.setLocalState(objectMapper.writeValueAsString(localStateForAppointment));
                    }
                    if (inputMessage.equals(today.plusDays(2).toString())) {
                        extracted(executionContext, today, docId);
                        localStateForAppointment.setStep("todayPlus2Day");
                        executionContext.setLocalState(objectMapper.writeValueAsString(localStateForAppointment));
                    }
                    if (inputMessage.equals(today.plusDays(3).toString())) {
                        extracted(executionContext, today, docId);
                        localStateForAppointment.setStep("todayPlus3Day");
                        executionContext.setLocalState(objectMapper.writeValueAsString(localStateForAppointment));
                    }
                    if (inputMessage.equals(today.plusDays(4).toString())) {
                        extracted(executionContext, today, docId);
                        localStateForAppointment.setStep("todayPlus4Day");
                        executionContext.setLocalState(objectMapper.writeValueAsString(localStateForAppointment));
                    }
                    break;
                case "today":

                    executionContext.createAppointmentToDoctor(today, inputMessage, String.valueOf(docId));
                    executionContext.setLocalState(null);
                    executionContext.setGlobalState(null);
                    break;
                case "todayPlusDay":
                    executionContext.createAppointmentToDoctor(today.plusDays(1), inputMessage, String.valueOf(docId));
                    executionContext.setLocalState(null);
                    executionContext.setGlobalState(null);
                    break;
                case "todayPlus2Day":
                    executionContext.createAppointmentToDoctor(today.plusDays(2), inputMessage, String.valueOf(docId));
                    executionContext.setLocalState(null);
                    executionContext.setGlobalState(null);
                    break;
                case "todayPlus3Day":
                    executionContext.createAppointmentToDoctor(today.plusDays(3), inputMessage, String.valueOf(docId));
                    executionContext.setLocalState(null);
                    executionContext.setGlobalState(null);
                    break;
                case "todayPlus4Day":
                    executionContext.createAppointmentToDoctor(today.plusDays(4), inputMessage, String.valueOf(docId));
                    executionContext.setLocalState(null);
                    executionContext.setGlobalState(null);
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