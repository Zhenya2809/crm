package com.evgeniy.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
@Setter
@ToString
@Table(name = "t_patientCard")

public class PatientCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientID")
    private Patient patient;
    @Column(name = "diagnosis")
    private String diagnosis;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorID")
    private Doctor doctor;

}
