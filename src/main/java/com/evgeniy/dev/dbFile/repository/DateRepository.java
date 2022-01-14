package com.evgeniy.dev.dbFile.repository;

import com.evgeniy.dev.dbFile.models.Date;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateRepository extends CrudRepository<Date, Integer> {

}
