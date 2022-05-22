package com.evgeniy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
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
        START_BOT_CHATTING,
        APPOINTMENT_TO_DOCTOR,
    }

    @Override
    public String toString() {
        return "DataUserTg{" +
                "chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", LocaleState='" + localeState + '\'' +
                ", GlobalState=" + globalState +
                ", email=" + email +
                '}';
    }
}

