package com.pm.students.service;

import com.pm.students.exceptions.StudentError;
import com.pm.students.exceptions.StudentException;
import com.pm.students.model.Status;
import com.pm.students.model.Student;
import com.pm.students.model.StudentDto;
import com.pm.students.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getAllStudentsOrByStatus(Status status) {
        if(status != null){
            return studentRepository.findByStatus(status);
        }
        return studentRepository.findAll();
    }

/*
    public List<Student> getAllStudents(Status status) {
        if (status == null) {
            return studentRepository.findAll();
        } else {
            return studentRepository.findAll()
                    .stream()
                    .filter(s -> s.getStatus().equals(status))
                    .collect(Collectors.toList());
        }
    }
*/

    public Student addStudent(Student student) {
        validateEmailExsisting(student);
        return studentRepository.save(student);
    }

    public Student getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));

        if(!Status.ACTIVE.equals(student.getStatus())){
           throw new StudentException(StudentError.STUDENT_IS_NOT_ACTICE);
        }
        return student;
    }

    public List<StudentDto> getStudentsByEmail(List<String> emails) {
        return studentRepository.findByEmailIn(emails)
                .stream()
                .map(StudentMapper::mapToDto)
                .collect(Collectors.toList());
    }


    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));

        student.setStatus(Status.INACTIVE);
        studentRepository.save(student);
    }

    public Student putStudent(Long studentId, Student student) {
        return studentRepository.findById(studentId)
                .map(studentFromDb -> {
                    if(!studentFromDb.getEmail().equals(student.getEmail()) &&
                        studentRepository.existsByEmail(student.getEmail())){
                        throw new StudentException(StudentError.STUDENT_EMAIL_ALREADY_EXIST);
                    }
                    studentFromDb.setFirstName(student.getFirstName());
                    studentFromDb.setLastName(student.getLastName());
                    studentFromDb.setEmail(student.getEmail());
                    studentFromDb.setStatus(student.getStatus());
                    return studentRepository.save(studentFromDb);
                }).orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    public Student patchStudent(Long student_Id, Student student) {
        return studentRepository.findById(student_Id)
                .map(studentFromDb -> {
                    if (!StringUtils.isEmpty(student.getFirstName())) {
                        studentFromDb.setFirstName(student.getFirstName());
                    }
                    if (!StringUtils.isEmpty(student.getLastName())) {
                        studentFromDb.setLastName(student.getLastName());
                    }
                    if (!StringUtils.isEmpty(student.getStatus())) {
                        studentFromDb.setStatus(student.getStatus());
                    }
                    return studentRepository.save(studentFromDb);
                }).orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }



    private void validateEmailExsisting(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new StudentException(StudentError.STUDENT_EMAIL_ALREADY_EXIST);
        }
    }



}
