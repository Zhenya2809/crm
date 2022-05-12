package com.evgeniy.commands;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.service.DoctorService;
import com.evgeniy.telegram.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class Personal implements Command {
    @Autowired
    public DoctorService doctorService;

    @Override
    public void doCommand(ExecutionContext executionContext) {
        executionContext.setGlobalState(DataUserTg.botstate.DOCTORS);

        doctorService.findAll().forEach(e -> {
            String speciality = e.getSpeciality();
            String fio = e.getFio();
            executionContext.replyMessage(speciality + " " + fio);
        });
        executionContext.sendKeyboardMainMenu();
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);


    }

    @Override
    public boolean shouldRunOnText(String text) {
        return text.equals("Доктора");
    }

    @Override
    public DataUserTg.botstate getGlobalState() {
        return DataUserTg.botstate.DOCTORS;
    }
}