package com.pm.courses.exceptions;

public enum CourseError {

    COURSE_NOT_FOUND("Course doesn't exist"),
    COURSE_CODE_ALREADY_EXIST("This code already exist"),
    COURSE_DATE_ERROR("Start date cannot be ealier than today and End date cannot be ealier than start day"),
    PARTICIPANTS_LIMIT_ERROR("Participants limit cannot be 0 and less than 0"),
    PARTICIPANTS_NUMBER_ERROR("Number of participants cannot be less than 0 and higher than participants limit"),
    COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED("Course already have max number of participants"),
    COURSE_IS_NOT_ACTIVE("Course is not active"),
    COURSE_CAN_NOT_SET_FULL_STATUS("Course can not set Full status"),
    COURSE_CAN_NOT_SET_ACTIVE_STATUS("Course can not set Full status. Participants limit i equals to Participants number"),

    COURSE_HAVE_NO_PARTICIPANTS("Course have no participants"),
    COURSE_IS_INACTIVE("Course is already inactive"),
    // Student
    STUDENT_IS_NOT_ACTIVE("Student is not active"),
    STUDENT_ALREADY_ENROLLED("Student already enrolled on this course");


    private final String message;

    CourseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
