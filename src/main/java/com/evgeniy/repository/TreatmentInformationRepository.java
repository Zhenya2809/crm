package com.evgeniy.repository;


import com.evgeniy.entity.Patient;
import com.evgeniy.entity.TreatmentInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TreatmentInformationRepository extends JpaRepository<TreatmentInformation, Long> {
//    Optional<TreatmentInformation> findTreatmentInformationByPatient(Patient patient);
    TreatmentInformation findTreatmentInformationByPatientCardId(Long id);
}
