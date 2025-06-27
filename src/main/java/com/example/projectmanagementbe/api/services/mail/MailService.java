package com.example.projectmanagementbe.api.services.mail;

import jakarta.mail.MessagingException;
import java.util.List;

public interface MailService {
    void sendProjectCreationNotification(List<String> toEmails, String projectName) throws MessagingException;
}
