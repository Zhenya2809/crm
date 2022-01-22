package com.evgeniy.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@RequiredArgsConstructor
@Setter
@ToString
@Table(name = "date")
public class AppointmentToDoctors implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "date")
    private String date;

    public AppointmentToDoctors(int id, String date, String time, String personFio, String clientFullName, String email) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.personFio = personFio;
        this.clientFullName = clientFullName;
        this.email = email;
    }

    @Column(name = "time")
    private String time;
    @Column(name = "personfio")
    private String personFio;
    @Column(name = "client")
    private String clientFullName;
    @Column(name = "email")
    private String email;

//    public AppointmentToDoctors(String date, String time, String personFio, String clientFullName, String email) {
//        this.date = date;
//        this.time = time;
//        this.personFio = personFio;
//        this.clientFullName = clientFullName;
//        this.email = email;
//    }
}
