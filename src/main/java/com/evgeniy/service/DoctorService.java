package com.evgeniy.service;

import com.evgeniy.entity.Doctor;
import com.evgeniy.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Configurable
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public void createrDoctor(String doctorFio, String speciality, String about, String photo) {
        Doctor doctor = new Doctor();
        doctor.setFio(doctorFio);
        doctor.setSpeciality(speciality);
        doctor.setAbout(about);
        doctor.setPhoto(photo);
        doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findDoctorById(id);
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public List<Doctor> findDoctorsBySperiality(String speciality) {
        return doctorRepository.findDoctorsBySpeciality(speciality);
    }

    public Doctor findDoctorByFio(String fio) {
        return doctorRepository.findDoctorsByFio(fio);
    }

    public Collection<Doctor> searchDoctor(@Param("speciality") String speciality,
                                           @Param("fio") String fio) {
        return doctorRepository.searchDoctor(speciality, fio);

    }
}
