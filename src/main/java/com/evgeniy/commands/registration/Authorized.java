package com.evgeniy.commands.registration;
import com.evgeniy.commands.registration.rolemenu.Admin;
import com.evgeniy.commands.registration.rolemenu.ChoseRole;
import com.evgeniy.commands.registration.rolemenu.Doctor;
import com.evgeniy.commands.registration.rolemenu.User;
import com.evgeniy.telegram.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class Authorized implements Registration {
    @Override
    public void execute(ExecutionContext executionContext) {

        String role = executionContext.getUser().getRole();
        Map<String, ChoseRole> map= new HashMap<>();
        map.put("USER",new User());
        map.put("ADMIN",new Admin());
        map.put("DOCTOR",new Doctor());
        ChoseRole choseRole = map.get(role);
        if (choseRole==null){
            throw new RuntimeException("fail to find by role");
        }
        choseRole.execute(executionContext);
        executionContext.setLocalState(null);
        executionContext.setGlobalState(null);
    }
}
