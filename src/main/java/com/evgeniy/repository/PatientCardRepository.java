package com.evgeniy.repository;

import com.evgeniy.entity.PatientCard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientCardRepository extends JpaRepository<PatientCard, Long> {

    PatientCard findPatientCardById(Long id);
}
