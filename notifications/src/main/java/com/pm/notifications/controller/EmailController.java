package com.pm.notifications.controller;

import com.pm.notifications.service.EmailSender;
import com.pm.notifications.model.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@Slf4j
public class EmailController {

    private final EmailSender emailSender;

    public EmailController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody @Valid EmailDto emailDto) {
        try {
            emailSender.sendEmail(emailDto);
        } catch (MessagingException e) {
            log.error("The message to " + emailDto.getTo() + " was not sent", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("The message to " + emailDto.getTo() + " was not sent");
        }
        return ResponseEntity.ok("Message was sent to " + emailDto.getTo());
    }
}
