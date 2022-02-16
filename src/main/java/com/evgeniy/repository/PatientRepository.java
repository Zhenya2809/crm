package com.evgeniy.repository;

import com.evgeniy.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);

    Iterable<Patient> findAllByEmail(String email);

    Patient findPatientById(Long id);

    Optional<Patient> findPatientByFio(String fio);

    Optional<Patient> findPatientByFioContains(String fio);



}
