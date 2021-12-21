package com.crm.crm.repository;

import com.crm.crm.springjpapostgres.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}