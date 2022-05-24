package com.evgeniy.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "t_patientInfo")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;
    @Column(name = "fio")
    private String fio;
    @Column(name = "sex")
    private String sex;
    @Column(name = "birthday")
    private String birthday;
    @Column(name = "placeOfResidence")
    private String placeOfResidence;
    @Column(name = "insurancePolicy")
    private String insurancePolicy;
    @Column(name = "email")
    private String email;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "patient")
    private List<AppointmentToDoctors> appointmentToDoctors;

}
