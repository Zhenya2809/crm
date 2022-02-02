package com.evgeniy.repository;

import com.evgeniy.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientInfoRepository extends JpaRepository<Patient, Long> {
    Patient findByEmail(String email);

}
