package com.evgeniy.repository;

import com.evgeniy.entity.InfoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface InfoDataRepository extends JpaRepository<InfoData,Long> {
    Optional<InfoData> findByData(String date);
}
