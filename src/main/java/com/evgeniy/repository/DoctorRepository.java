package com.evgeniy.repository;

import com.evgeniy.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findDoctorById(Long id);

    List<Doctor> findDoctorsBySpeciality(String speciality);

    Doctor findDoctorsByFio(String fio);

    @Query(value = "select * from t_doctor where lower(speciality) like %:speciality% and lower(fio) like %:fio% limit 50", nativeQuery = true)
    Collection<Doctor> searchDoctor(@Param("speciality") String speciality,
                                    @Param("fio") String fio);

}
