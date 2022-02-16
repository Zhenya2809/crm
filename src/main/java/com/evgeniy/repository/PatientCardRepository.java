package com.evgeniy.repository;

import com.evgeniy.entity.Patient;
import com.evgeniy.entity.PatientCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PatientCardRepository extends JpaRepository<PatientCard, Long> {

    PatientCard findPatientCardById(Long id);
    Optional<PatientCard> findPatientCardByPatient(Patient patient);
    PatientCard findPatientCardByPatientId(Long id);

}
