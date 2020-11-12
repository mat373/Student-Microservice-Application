package com.pm.notifications.service;

import com.pm.notifications.model.EmailDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
public class EmailSenderTest {

    @Autowired
    EmailSender emailSender;

    @Test
    public void send_email_test() throws MessagingException {
        EmailDto emailDto = EmailDto.builder()
                .to("matuszewski.ptr@gmail.com")
                .title("Course Enrollment")
                .content("Test_1")
                .build();
        emailSender.sendEmail(emailDto);
    }
}
