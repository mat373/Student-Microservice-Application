## Student-Microservice-Application

#### #Spring Boot #Spring Web #Spring Data JPA #Spring Cloud #MongoDB #PostgresSQL #RabbitMQ

The application allows you to add students and courses as well as enrollment in courses. The application consists of:
- StudentService - an application for managing the student database
- CourseService - an application for managing the course database and course enrollment.
- NotificationService - application for sending notifications (e-mail) supporting registration for the course.
- Eureka Server
- Gateway

## Documentation

The applications use Swagger. Run the Student-Microservice-Application and check:

Documentation for student-service
```bash
http://localhost:8080/swagger-ui.html
```
Documentation for course-service
```bash
http://localhost:8087/swagger-ui.html
```

## Run
```
git clone https://github.com/mat373/Student-Microservice-Application.git
```
-> import maven dependencies
-> Multirun configuration will be detected
-> Run all application in the given order

