package com.evgeniy.repository;

import com.evgeniy.entity.AppointmentToDoctors;
import com.evgeniy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentToDoctors, Long> {
}

