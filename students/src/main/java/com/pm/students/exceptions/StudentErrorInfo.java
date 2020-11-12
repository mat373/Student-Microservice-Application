package com.pm.students.exceptions;

public class StudentErrorInfo {

    private final String message;

    public StudentErrorInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
