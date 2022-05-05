package com.evgeniy.service;

import com.evgeniy.entity.DataUserTg;
import com.evgeniy.repository.DataUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DataUserService {
    @Autowired
    private DataUserRepository dataUserRepository;

    public Optional<DataUserTg> findDataUserByChatId(Long id) {
        return Optional.ofNullable(dataUserRepository.findDataUserByChatId(id));
    }

    public void createUser(Long chatId, String firstName, String lastName) {
        DataUserTg user = new DataUserTg();
        user.setChatId(chatId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setGlobalState(DataUserTg.botstate.START);
        user.setLocaleState("Main_menu");
        dataUserRepository.save(user);
        log.info("user created : chatId=" + chatId + " firstName=" + firstName + " lastName=" + lastName);
    }

    public void save(DataUserTg user) {
        dataUserRepository.save(user);
    }
    public List<Long> findAll(){
        List<Long> chatIdList=new ArrayList<>();
        dataUserRepository.findAll().forEach(e-> {
            Long chatId = e.getChatId();
            chatIdList.add(chatId);
        });
        return chatIdList;
    }

}
