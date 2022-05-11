package com.evgeniy.service;

import com.evgeniy.repository.KozelecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KozelecService {

    @Autowired
    KozelecRepository kozelecRepository;
//
//    SELECT * FROM ukrposhta_houses uh  where region like '%region%' and district like '%district%'  and street like '%street%' and "number" like '%number%'



}
