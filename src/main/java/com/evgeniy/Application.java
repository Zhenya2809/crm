package com.evgeniy;

import com.evgeniy.dev.CallBack;
import com.evgeniy.email.SendEmailTLS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
//        SendEmailTLS sendEmailTLS = new SendEmailTLS();
//        sendEmailTLS.SendEmail("testsubject", "zhenya.gricyk@gmail.com", "testTextofMessage");

        CallBack callBack = new CallBack();
        callBack.printList();
    }

}
