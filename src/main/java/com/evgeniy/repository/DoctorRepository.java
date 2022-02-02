package com.evgeniy.repository;

import com.evgeniy.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findDoctorById(Long id);
}
