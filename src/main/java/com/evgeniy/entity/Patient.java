package com.evgeniy.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Setter
@ToString
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
}
