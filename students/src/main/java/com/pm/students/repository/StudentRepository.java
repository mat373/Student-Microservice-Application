package com.pm.students.repository;

import com.pm.students.model.Status;
import com.pm.students.model.Student;
import com.pm.students.model.StudentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    List<Student> findByStatus(Status status);

    List<Student> findByEmailIn(List<String> emails);

}
