package com.evgeniy.repository;


import com.evgeniy.entity.TreatmentInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentInformationRepository extends JpaRepository<TreatmentInformation, Long> {
}
