package com.pm.courses.service;

import com.pm.courses.model.Course;
import com.pm.courses.model.Status;
import com.pm.courses.model.Student;

import java.util.List;

public interface CourseService {

    List<Course> getCoursesByStatus(Status status);
    Course getCourse(String code);
    Course addCourse(Course course);
    void delete(String code);
    Course putCourse(String code, Course course);
    Course patchCourse(String code, Course course);

    //Course enrollment
    void courseEnrollment(String courseId, Long studentId);
    List<Student> getCourseMembers(String courseId);
    void finishCourseEnrollment(String courseId);

}
