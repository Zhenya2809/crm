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
@Table(name = "patientCard")
public class PatientCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    @Column(name = "startDate")
    private String startDate;
    @Column(name = "finishDate")
    private String finishDate;
    @Column(name = "diagnosis")
    private String diagnosis;
    @Column(name = "IDdoctor")
    private String IDdoctor;
    @Column(name = "email")
    private String email;
}
