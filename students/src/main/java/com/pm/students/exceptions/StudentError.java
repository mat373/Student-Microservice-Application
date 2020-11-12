package com.pm.students.exceptions;

public enum  StudentError {

    STUDENT_NOT_FOUND("Student doesn't exist"),
    STUDENT_EMAIL_ALREADY_EXIST("This email already exist"),
    STUDENT_IS_NOT_ACTICE ("Student is not active");

    private final String message;

    StudentError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
