package com.pm.courses.service;

import com.pm.courses.exceptions.CourseError;
import com.pm.courses.exceptions.CourseException;
import com.pm.courses.model.*;
import com.pm.courses.repository.CourseRepository;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CourseServiceImpl implements CourseService {

    private static final String EXCHANGE_ENROLL_FINISH = "enroll_finish";
    
    private final CourseRepository courseRepository;
    private final StudentServiceClient studentServiceClient;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, StudentServiceClient studentServiceClient, RabbitTemplate rabbitTemplate) {
        this.courseRepository = courseRepository;
        this.studentServiceClient = studentServiceClient;
        this.rabbitTemplate = rabbitTemplate;
    }


    public List<Course> getCoursesByStatus(Status status) {
        if (status != null) {
            return courseRepository.findAllByStatus(status);
        } else {
            return courseRepository.findAll();
        }
    }


    public Course getCourse(String code) {
        Course course = courseRepository.findById(code)
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));

        if (Status.INACTIVE.equals(course.getStatus())) {
            throw new CourseException(CourseError.COURSE_IS_NOT_ACTIVE);
        }

        return course;
    }

    public Course addCourse(Course course) {

        if (courseRepository.findById(course.getCode()).isPresent()) {
            throw new CourseException(CourseError.COURSE_CODE_ALREADY_EXIST);
        }
        course.validateCourse();

        return courseRepository.save(course);
    }

    public void delete(String code) {
        Course course = courseRepository.findById(code)
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));

        course.setStatus(Status.INACTIVE);
        courseRepository.save(course);
    }

    public Course putCourse(String code, Course course) {
        return courseRepository.findById(code)
                .map(courseFromDb -> {

                    courseFromDb.setDescription(course.getDescription());
                    courseFromDb.setName(course.getName());
                    courseFromDb.setStartDate(course.getStartDate());
                    courseFromDb.setEndDate(course.getEndDate());
                    courseFromDb.setParticipantsLimit(course.getParticipantsLimit());
                    courseFromDb.setParticipantsNumber(course.getParticipantsNumber());
                    courseFromDb.setStatus(course.getStatus());

                    course.validateCourse();

                    return courseRepository.save(courseFromDb);
                }).orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }


    public Course patchCourse(String code, Course course) {
        return courseRepository.findById(code)
                .map(courseFromDb -> {

                    if (!StringUtils.isEmpty(course.getName())) {
                        courseFromDb.setName(course.getName());
                    }
                    if (!StringUtils.isEmpty(course.getDescription())) {
                        courseFromDb.setDescription(course.getDescription());
                    }
                    if (!StringUtils.isEmpty(course.getStartDate())) {
                        courseFromDb.setStartDate(course.getStartDate());
                    }
                    if (!StringUtils.isEmpty(course.getEndDate())) {
                        courseFromDb.setEndDate(course.getEndDate());
                    }
                    if (!StringUtils.isEmpty(course.getParticipantsLimit())) {
                        courseFromDb.setParticipantsLimit(course.getParticipantsLimit());
                    }
                    if (!StringUtils.isEmpty(course.getParticipantsNumber())) {
                        courseFromDb.setParticipantsNumber(course.getParticipantsNumber());
                    }
                    if (!StringUtils.isEmpty(course.getStatus())) {
                        courseFromDb.setStatus(course.getStatus());
                    }

                    course.validateCourse();

                    return courseRepository.save(courseFromDb);
                }).orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    // ======================COURSE ENROLLMENT =============================

    public void courseEnrollment(String course_id, Long studentId) {
        Course course = getCourse(course_id);
        validateCourseActiveStatus(course);

        Student student = studentServiceClient.getStudentById(studentId);
        validateStudentBeforeCourseEnrollment(course, student);

        course.incrementParticipantsNumber();

        course.getCourseMember().add(new CourseMember(student.getEmail()));
        courseRepository.save(course);
    }

    public List<Student> getCourseMembers(String courseId) {
        Course course = getCourse(courseId);
        // nie bedzie null bo zawsze jest inicjowana pusta lista
        List<@NotNull String> emailsMembers = getCourseMembersEmailsList(course);

        return studentServiceClient.getStudentsByEmails(emailsMembers);
    }


    public void finishCourseEnrollment(String courseId) {
        Course course = getCourse(courseId);
        validateCourseInactiveStatus(course);
        course.setStatus(Status.INACTIVE);
        sendMessageToRabbitMq(course);
        courseRepository.save(course);
    }

    private void sendMessageToRabbitMq(Course course) {
        NotificationInfoDto notificationInfo = createNotificationToSend(course);
        rabbitTemplate.convertAndSend(EXCHANGE_ENROLL_FINISH, notificationInfo);
    }

    private NotificationInfoDto createNotificationToSend(Course course) {
        List<@NotNull String> emailsMembers = getCourseMembersEmailsList(course);

        return NotificationInfoDto.builder()
                .courseCode(course.getCode())
                .courseName(course.getName())
                .courseDescription(course.getDescription())
                .courseStartDate(course.getStartDate())
                .courseEndDate(course.getEndDate())
                .emails(emailsMembers)
                .build();
    }

    private void validateStudentBeforeCourseEnrollment(Course course, Student student) {
        if (!student.getStatus().equals(Student.Status.ACTIVE)) {
            throw new CourseException(CourseError.STUDENT_IS_NOT_ACTIVE);
        }

        if (course.getCourseMember().stream()
                .anyMatch(member -> student.getEmail().equals(member.getEmail()))) {
            throw new CourseException(CourseError.STUDENT_ALREADY_ENROLLED);
        }
    }

    private void validateCourseInactiveStatus(Course course){
        if(course.getStatus().equals(Status.INACTIVE)){
            throw new CourseException(CourseError.COURSE_IS_INACTIVE);
        }
    }

    private void validateCourseActiveStatus(Course course) {
        if (!course.getStatus().equals(Status.ACTIVE)) {
            throw new CourseException(CourseError.COURSE_IS_NOT_ACTIVE);
        }
    }

    private List<@NotNull String> getCourseMembersEmailsList(Course course) {
        return course.getCourseMember().stream()
                .map(CourseMember::getEmail)
                .collect(Collectors.toList());
    }
}


