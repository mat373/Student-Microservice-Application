package com.pm.students.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentExceptionHandler {

    @ExceptionHandler(value = StudentException.class)
    public ResponseEntity<StudentErrorInfo> handleException(StudentException e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (StudentError.STUDENT_NOT_FOUND.equals(e.getStudentError())) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (StudentError.STUDENT_EMAIL_ALREADY_EXIST.equals(e.getStudentError())) {
            httpStatus = HttpStatus.CONFLICT;
        } else if (StudentError.STUDENT_IS_NOT_ACTICE.equals(e.getStudentError())){
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(httpStatus).body(new StudentErrorInfo(e.getStudentError().getMessage()));
    }
}
