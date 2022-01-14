package com.evgeniy.dev.dbFile.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class Users implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "login")
    private String login;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;


    public Users() {
    }


    public Users(String email, String password, String firstName, String lastName, String login) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;

    }
}
