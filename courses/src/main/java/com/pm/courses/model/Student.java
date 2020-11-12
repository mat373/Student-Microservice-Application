package com.pm.courses.model;
;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Student {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;

    @NotNull
    private Status status;

    public Student(){}

    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
