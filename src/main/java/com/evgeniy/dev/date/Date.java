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

    @Column(name = "date")
    private String date;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "time")
    private String time;

    public Date(String date, String time) {
        this.date = date;
        this.time = time;
    }
}
