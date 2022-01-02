package com.evgeniy.dev.dbFile.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class Users implements Serializable {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    public Users() {
    }

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
