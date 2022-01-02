package com.evgeniy.dev.dbFile.repository;

import com.evgeniy.dev.dbFile.models.ContactData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<ContactData, String> {


}
