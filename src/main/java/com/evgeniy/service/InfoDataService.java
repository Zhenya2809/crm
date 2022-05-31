package com.evgeniy.service;

import com.evgeniy.entity.InfoData;
import com.evgeniy.repository.InfoDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Configurable
public class InfoDataService {
    @Autowired
    InfoDataRepository infoDataRepository;

    public void setAdministratorToday(Long admin_id) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);

        Optional<InfoData> adminDate = infoDataRepository.findByData(format);
        if (adminDate.isPresent()) {
            InfoData infoData = adminDate.get();
            infoData.setAdministrator_id(admin_id);
            infoData.setData(format);
            infoDataRepository.save(infoData);
            log.info("infoData edit:" + "date setup=" + format + "\n" + "admin_id setup=" + admin_id);
        } else {
            InfoData infoData = new InfoData();
            infoData.setData(format);
            infoData.setAdministrator_id(admin_id);
            infoDataRepository.save(infoData);
            log.info("infoData create:" + "date setup=" + format + "\n" + "admin_id setup=" + admin_id);
        }
    }

    public Long getAdministratorId() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        Optional<InfoData> adminDate = infoDataRepository.findByData(format);
        if (adminDate.isPresent()) {
            return adminDate.get().getAdministrator_id();
        }
        throw new RuntimeException("administrator not found");

    }
}
