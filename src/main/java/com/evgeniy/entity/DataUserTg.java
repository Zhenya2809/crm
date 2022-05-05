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
    private String LocaleState;
    @Column(name = "globalState")
    public botstate GlobalState;


    public DataUserTg(Long chatId, String firstName, String lastName) {
        this.chatId = chatId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public enum botstate {
        START,
        SEND_ALL_MESSAGE
    }

    @Override
    public String toString() {
        return String.format("chatID:: ,%s, firstName:: ,%s,  lastName: ,%s,", this.chatId, this.firstName, this.lastName);
    }
}

