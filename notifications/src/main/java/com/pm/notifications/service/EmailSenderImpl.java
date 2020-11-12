package com.pm.notifications.service;

import com.pm.notifications.model.EmailDto;
import com.pm.notifications.model.NotificationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender javaMailSender;

    public EmailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmails(NotificationInfo notificationInfo) {
        String title = "Pamiętaj o kursie: " + notificationInfo.getCourseName();
        StringBuilder content = createEmailContent(notificationInfo);
        notificationInfo.getEmails().forEach(email -> {
            try {
                sendEmail(email,title,content.toString());
            } catch (MessagingException e) {
                log.error("Notyfikacja nie została wysłana", e);
            }
        });
    }

    public void sendEmail(EmailDto emailDto) throws MessagingException {
        sendEmail(emailDto.getTo(), emailDto.getTitle(), emailDto.getContent());
    }


       private void sendEmail(String to, String title, String content) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(title);
        mimeMessageHelper.setText(content, false);
        javaMailSender.send(mimeMessage);
    }

    private StringBuilder createEmailContent(NotificationInfo notificationInfo) {
        StringBuilder content = new StringBuilder();
        content.append("Kurs ");
        content.append(notificationInfo.getCourseName());
        content.append(" rozpoczyna się ");
        content.append(notificationInfo.getCourseStartDate().toLocalDate());
        content.append(" o godzinie ");
        content.append(notificationInfo.getCourseStartDate().getHour()).append(":").append(notificationInfo.getCourseStartDate().getMinute());
        content.append(". Proszę być 15 minut przed rozpoczęciem");
        content.append("\n");
        content.append("Opis kursu ");
        content.append(notificationInfo.getCourseDescription());
        content.append("\n");
        content.append("Kurs kończy się ");
        content.append(notificationInfo.getCourseStartDate().toLocalDate());
        content.append("\n");
        content.append("Czekamy na Ciebie ! Do zobaczenia !");
        return content;
    }
}
