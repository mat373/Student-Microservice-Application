package com.pm.notifications.service;

import com.pm.notifications.model.NotificationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMqListenerInpl implements RabbitMqListener {

    private final EmailSender emailSender;

    public RabbitMqListenerInpl(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @RabbitListener(queues = "enroll_finish")
    public void handlerFinishEnroll(NotificationInfo notificationInfo) {
        emailSender.sendEmails(notificationInfo);
    }
}
