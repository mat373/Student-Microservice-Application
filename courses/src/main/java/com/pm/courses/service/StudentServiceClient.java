package com.pm.courses.service;

import com.pm.courses.model.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "STUDENT-SERVICE")
@RequestMapping("/students")
public interface StudentServiceClient {

    @GetMapping("/{studentId}")
    Student getStudentById(@PathVariable Long studentId);

    @PostMapping("/emails")
    List<Student> getStudentsByEmails(@RequestBody List<String> emails);
}
