package com.evgeniy.dev.dbFile.repository;


import com.evgeniy.dev.dbFile.models.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {
}
