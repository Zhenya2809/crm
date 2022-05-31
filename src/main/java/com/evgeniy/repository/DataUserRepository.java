package com.evgeniy.repository;

import com.evgeniy.entity.DataUserTg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataUserRepository extends JpaRepository<DataUserTg, Long> {
    DataUserTg findDataUserByChatId(Long id);
    Optional<DataUserTg> findDataUserTgByChatId(Long id);


}
