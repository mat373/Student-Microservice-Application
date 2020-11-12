package com.pm.courses.model;

import com.pm.courses.exceptions.CourseError;
import com.pm.courses.exceptions.CourseException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
public class Course implements Serializable {

    @Id
    private String code;

    @NotBlank
    private String name;
    private String description;
    @NotNull
//    @Future
//    @JsonFormat
    private LocalDateTime startDate;
    @NotNull
//    @Future
//    @JsonFormat
    private LocalDateTime endDate;
    @Min(0)
    private Long participantsLimit;
    @NotNull
    @Min(0)
    private Long participantsNumber;

    //new arraylisty dlatego nie bedzie nullem zawsze zainicjalizowana
    private List<CourseMember> courseMember = new ArrayList<>();

    @NotNull
    private Status status;

    public void validateCourse(){
        validateCourseDate();
        validateCourseParticipantsLimit();
        validateStatus();
    }

    public void incrementParticipantsNumber(){
       participantsNumber++;
        if(participantsLimit.equals(participantsNumber)){
           setStatus(Status.FULL);
        }
    }

    private void validateCourseDate() {
        if (startDate.isBefore(LocalDateTime.now()) ||endDate.isBefore(startDate)) {
            throw new CourseException(CourseError.COURSE_DATE_ERROR);
        }
    }

    private void validateCourseParticipantsLimit() {

        if (participantsLimit <= 0) {
            throw new CourseException(CourseError.PARTICIPANTS_LIMIT_ERROR);
        }
        if (participantsNumber < 0 || participantsNumber > participantsLimit) {
            throw new CourseException(CourseError.PARTICIPANTS_NUMBER_ERROR);
        }
    }

    private void validateStatus(){
        if (status.equals(Status.FULL) && !participantsLimit.equals(participantsNumber)) {
            throw new CourseException(CourseError.COURSE_CAN_NOT_SET_FULL_STATUS);
        }
        if(status.equals(Status.ACTIVE) && participantsLimit.equals(participantsNumber)) {
            throw new CourseException(CourseError.COURSE_CAN_NOT_SET_ACTIVE_STATUS);
        }
    }
}
