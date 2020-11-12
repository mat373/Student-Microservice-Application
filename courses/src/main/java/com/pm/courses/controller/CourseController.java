package com.pm.courses.controller;

import com.pm.courses.model.Course;
import com.pm.courses.model.Status;
import com.pm.courses.model.Student;
import com.pm.courses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getCourses(@RequestParam(required = false)Status status) {
       return courseService.getCoursesByStatus(status);
    }

    @PostMapping
    public Course addCourse(@RequestBody @Valid Course course) {
        return courseService.addCourse(course);
    }

    @GetMapping("/{code}")
    public Course getCourse(@PathVariable String code) {
        return courseService.getCourse(code);

    }

    @DeleteMapping("/{code}")
    public void deleteCourse(@PathVariable String code) {
        courseService.delete(code);
    }

    @PutMapping("/{code}")
    public Course putCourse(@PathVariable String code, @Valid @RequestBody Course course) {
        return courseService.putCourse(code, course);
    }

    @PatchMapping("/{code}")
    public Course patchCourse(@PathVariable String code, @RequestBody Course course) {
        return courseService.patchCourse(code,course);
    }

    // ==================== Course Enrollment =======================

    @GetMapping("/{courseId}/members")
    public List<Student> getCourseMembers(@PathVariable String courseId){
        return courseService.getCourseMembers(courseId);
    }

    @PostMapping("/{courseId}/student/{studentId}")
    public ResponseEntity<?> courseEnrollment(@PathVariable  String courseId,
                                              @PathVariable Long studentId){
        courseService.courseEnrollment(courseId,studentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{courseId}/finish-enroll")
    public ResponseEntity<?> courseFinishEnroll(@PathVariable String courseId){
        courseService.finishCourseEnrollment(courseId);
        return ResponseEntity.ok().build();
    }
}
