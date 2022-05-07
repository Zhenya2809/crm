package com.evgeniy.service;

import com.evgeniy.entity.Doctor;
import com.evgeniy.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor createrDoctor(String doctorFio,String speciality){
        Doctor doctor = new Doctor();
        doctor.setFio(doctorFio);
        doctor.setSpeciality(speciality);
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id){
        doctorRepository.deleteById(id);
    }
    public Doctor getDoctorById(Long id){
       return doctorRepository.findDoctorById(id);
    }
    public List<Doctor> findAll(){
        return doctorRepository.findAll();
    }
}
