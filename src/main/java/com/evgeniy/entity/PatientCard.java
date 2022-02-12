package com.evgeniy.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "t_patientCard")

public class PatientCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Patient patient;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "patientCard")
    @Column(name = "treatment_information")
    private Set<TreatmentInformation> treatmentInformation;


}
