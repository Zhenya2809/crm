package com.evgeniy.repository;

import com.evgeniy.entity.Kozelec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface KozelecRepository extends JpaRepository<Kozelec, Long> {
    @Query(value = "SELECT * FROM ukrposhta_houses uh  where LOWER(region) like %:region%  and LOWER(title) like %:title%" +
                   " and LOWER(street) like %:street% and LOWER(number) like %:number% limit 50 ", nativeQuery = true)
    Collection<Kozelec> searchAddress(@Param("region") String region,
                                      @Param("title") String city,
                                      @Param("street") String street,
                                      @Param("number") String number);
}
