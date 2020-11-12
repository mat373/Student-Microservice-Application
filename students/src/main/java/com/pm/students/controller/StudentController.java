package com.pm.students.controller;

import com.pm.students.model.Status;
import com.pm.students.model.Student;
import com.pm.students.model.StudentDto;
import com.pm.students.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents(@RequestParam(required = false)Status status) {
        return studentService.getAllStudentsOrByStatus(status);
    }

    @PostMapping
    public Student addStudent(@RequestBody @Valid Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudentById(id);

    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
       studentService.deleteStudent(id);
    }

    @PutMapping("/{id}")
    public Student putStudent(@PathVariable Long id, @Valid @RequestBody Student student) {
        return studentService.putStudent(id, student);
    }

    @PatchMapping("/{id}")
    public Student patchStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.patchStudent(id,student);
    }

    // ===========================

    @PostMapping("/emails")
    public List<StudentDto> getCourseMembersByEmail(@RequestBody List<String> emails){
        return studentService.getStudentsByEmail(emails);
    }

}
