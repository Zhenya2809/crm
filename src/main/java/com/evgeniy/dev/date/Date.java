package com.evgeniy.dev.date;

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
public class Date implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "date")
    private String date;
    @Column(name = "time")
    private String time;
    @Column(name = "personfio")
    private String personFio;
    @Column(name="client")
    private String clientFullName;

    public Date(String date, String time, String personFio,String clientFullName) {
        this.date = date;
        this.time = time;
        this.personFio = personFio;
        this.clientFullName=clientFullName;
    }
}
