package com.pm.courses.repository;

import com.pm.courses.model.Course;
import com.pm.courses.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course, String> {

    List<Course> findAllByStatus(Status status);
}
