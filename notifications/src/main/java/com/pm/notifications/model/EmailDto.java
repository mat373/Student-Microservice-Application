package com.pm.notifications.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class EmailDto {

    @NotBlank
    @Email
    private String to;
    @NotBlank
    private String title;
    @NotBlank
    private String content;

}
