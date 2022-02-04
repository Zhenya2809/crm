package com.evgeniy.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "t_doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long id;
    @Column(name = "fio")
    private String fio;
    @Column(name = "speciality")
    private String speciality;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<AppointmentToDoctors> appointmentToDoctors;

}
