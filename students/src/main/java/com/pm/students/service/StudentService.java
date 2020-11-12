package com.pm.students.service;


import com.pm.students.model.Status;
import com.pm.students.model.Student;
import com.pm.students.model.StudentDto;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudentsOrByStatus(Status status);

//    List<Student> getAllStudents( Status status);

    Student getStudentById(Long studentId);
    List<StudentDto> getStudentsByEmail(List<String> emails);

    Student addStudent(Student student);

    void deleteStudent(Long studentId);

    Student putStudent(Long studentId, Student student);

    Student patchStudent(Long student_Id, Student student);

}
