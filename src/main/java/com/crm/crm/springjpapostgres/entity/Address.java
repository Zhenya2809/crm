package com.crm.crm.springjpapostgres.entity;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String city;

    @Column
    private String street;

    @Column(name = "home_number")
    private String homeNumber;

//getters and setters
}