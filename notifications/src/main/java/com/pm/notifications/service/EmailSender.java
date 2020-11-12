package com.pm.notifications.service;

import com.pm.notifications.model.EmailDto;
import com.pm.notifications.model.NotificationInfo;

import javax.mail.MessagingException;

public interface EmailSender {

    void sendEmails(NotificationInfo notificationInfo);
    void sendEmail(EmailDto emailDto) throws MessagingException;
//    void sendEmail(String to, String title, String content) throws MessagingException;
}
