package com.pm.courses.exceptions;

public class CourseErrorInfo {

    private final String message;

    public CourseErrorInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
