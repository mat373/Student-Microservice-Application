package com.pm.students.service;

import com.pm.students.model.Student;
import com.pm.students.model.StudentDto;

public class StudentMapper {

    public static StudentDto mapToDto(Student student){
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setEmail(student.getEmail());
        return studentDto;
    }
}
