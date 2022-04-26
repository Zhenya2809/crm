package com.evgeniy.repository;

import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.entity.Doctor;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentToDoctors, Long> {
    Iterable<AppointmentToDoctors> findAppointmentToDoctorsByDoctor(Doctor doctor);
    List<AppointmentToDoctors> findAllByDoctor_Id(Long id);
    List<AppointmentToDoctors> findAllByPatientId(Long id);
    AppointmentToDoctors findAppointmentToDoctorsByDoctorsappointmentsID(Long id);

}

