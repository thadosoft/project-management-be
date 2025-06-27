package com.example.projectmanagementbe.api.services.impls.mail;

import com.example.projectmanagementbe.api.services.mail.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendProjectCreationNotification(List<String> toEmails, String projectName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setTo("tungh3210@gmail.com");  // hoặc chính email của bạn
        helper.setBcc(toEmails.toArray(new String[0])); // ẩn danh sách
            helper.setSubject("Dự Án Mới");
            helper.setText("Một dự án tên \"" + projectName + "\" đã được tạo.", true);
            mailSender.send(message);
        }
    }

