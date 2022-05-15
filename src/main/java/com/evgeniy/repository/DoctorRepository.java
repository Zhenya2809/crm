package com.evgeniy.repository;

import com.evgeniy.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findDoctorById(Long id);
    List<Doctor> findDoctorsBySpeciality(String speciality);
    Doctor findDoctorsByFio(String fio);

}
