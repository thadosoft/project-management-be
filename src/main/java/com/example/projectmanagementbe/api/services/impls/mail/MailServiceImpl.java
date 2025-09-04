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
        for (String to : toEmails) {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("Dự án mới: " + projectName);
            helper.setText("<p>Bạn đã được thêm vào dự án: <strong>" + projectName + "</strong></p>", true);
            mailSender.send(message);
        }
    }

    @Override
    public void sendAssignmentNotification(String toEmail, String assignmentTitle, String taskStatus, String assignerName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("Bạn vừa được giao một nhiệm vụ mới!");

        String content = String.format("""
            <div style="font-family: sans-serif;">
                <h3>Xin chào,</h3>
                <p>Bạn vừa được giao một nhiệm vụ mới trong hệ thống quản lý dự án:</p>
                <ul>
                    <li><strong>Tên nhiệm vụ:</strong> %s</li>
                    <li><strong>Tên task:</strong> %s</li>
                    <li><strong>Người giao:</strong> %s </li>
                </ul>
                <p>Vui lòng truy cập hệ thống để xem chi tiết và bắt đầu công việc.</p>
                <br/>
                <p style="color: gray;">-- Project Management System</p>
            </div>
            """, assignmentTitle, taskStatus, assignerName);

        helper.setText(content, true);

        mailSender.send(message);
    }
}