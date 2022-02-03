package com.evgeniy.repository;

import com.evgeniy.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByEmail(String email);
    Iterable<Patient> findAllByEmail(String email);
    Patient findPatientById(Long id);


}
