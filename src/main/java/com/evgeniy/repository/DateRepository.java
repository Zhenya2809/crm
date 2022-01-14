package com.evgeniy.repository;

import com.evgeniy.entity.Date;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateRepository extends CrudRepository<Date, Integer> {

}
