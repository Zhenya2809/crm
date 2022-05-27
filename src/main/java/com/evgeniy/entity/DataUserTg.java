package com.evgeniy.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "b_user")
public class DataUserTg {

    @Id
    private Long chatId;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "localState")
    private String localeState;
    @Enumerated(EnumType.STRING)
    @Column(name = "globalState")
    public botstate globalState;
    @Column(name = "email")
    public String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "administrator",columnDefinition = "boolean default false")
    private boolean isAdministrator;

    public DataUserTg(Long chatId, String firstName, String lastName) {
        this.chatId = chatId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public enum botstate {
        START,
        YES,
        NO,
        SPECIALISTS,
        ABOUT,
        ABOUT_CLINIC_TEXT,
        COSMETICS,
        SERVICES,
        ADDRESS,
        DOCTORS,
        SHOW_SITE,
        MAIN_MENU,
        MY_APPOINTMENTS,
        START_BOT_CHATTING,
        APPOINTMENT_TO_DOCTOR,
        CONTACT,
        CALL_BACK,
        ADMINISTRATORS
    }
}

