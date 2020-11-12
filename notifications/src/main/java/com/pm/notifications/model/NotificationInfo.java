package com.pm.notifications.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class NotificationInfo {

    private List<String> emails;
    private String courseCode;
    private String courseName;
    private String courseDescription;
    @JsonFormat(pattern = "yyy-MM-dd'T'HH:mm")
    private LocalDateTime courseStartDate;
    @JsonFormat(pattern = "yyy-MM-dd'T'HH:mm")
    private LocalDateTime courseEndDate;
}
