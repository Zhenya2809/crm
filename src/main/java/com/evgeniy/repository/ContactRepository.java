package com.evgeniy.repository;

import com.evgeniy.entity.ContactData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<ContactData, String> {


}
