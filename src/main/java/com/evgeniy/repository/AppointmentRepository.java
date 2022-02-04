package com.evgeniy.repository;

import com.evgeniy.entity.AppointmentToDoctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentToDoctors, Long> {
}

