package com.pm.courses.exceptions;


public class CourseException extends RuntimeException {

    private final CourseError courseError;

    public CourseException(CourseError courseError){
        this.courseError = courseError;
    }

    public CourseError getCourseError() {
        return courseError;
    }
}
