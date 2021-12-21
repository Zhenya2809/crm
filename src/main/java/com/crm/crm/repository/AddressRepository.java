package com.crm.crm.repository;


import com.crm.crm.springjpapostgres.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}