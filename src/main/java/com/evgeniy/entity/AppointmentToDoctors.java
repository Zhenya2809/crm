package com.evgeniy.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@Setter
@ToString
@Table(name = "t_doctorsappointment")
public class AppointmentToDoctors implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "date")
    private String date;
    @Column(name = "time")
    private String time;
    @Column(name = "doctorfio")
    private String doctorFIO;
    @Column(name = "clientfio")
    private String clientFullName;
    @Column(name = "email")
    private String email;

}
