package com.evgeniy.repository;

import com.evgeniy.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findStudentById(Long id);
}
