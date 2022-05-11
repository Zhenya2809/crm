package com.evgeniy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "ukrposhta_houses")
public class Kozelec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "region")
    private String region;

    @Column(name = "district")
    private String district;

    @Column(name = "title")
    private String title;

    @Column(name = "zip")
    private String zip;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private String number;


}
