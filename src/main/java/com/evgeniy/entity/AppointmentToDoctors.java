package com.evgeniy.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "t_doctorsappointments")
public class AppointmentToDoctors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_doctorsappointments")
    private Long doctorsappointmentsID;
    @Column(name = "date")
    private String date;
    @Column(name = "time")
    private String time;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorID")
    private Doctor doctor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientID")
    private Patient patient;


}
