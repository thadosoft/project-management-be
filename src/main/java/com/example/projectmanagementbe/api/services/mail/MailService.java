package com.example.projectmanagementbe.api.services.mail;

import jakarta.mail.MessagingException;
import java.time.LocalDate;
import java.util.List;

public interface MailService {
    void sendAssignmentNotification(String toEmail, String assignmentTitle, String taskStatus, String assignerName) throws MessagingException;

    void sendProjectCreationNotification(List<String> toEmails, String projectName) throws MessagingException;

    /**
     * Notifies the OFM group that a new leave request is awaiting approval. Failures are swallowed.
     *
     * @param remainingLeave the requester's current remaining balance for the year (may be null)
     */
    void sendLeaveRequestNotification(List<String> toEmails, String employeeName, LocalDate startDate,
        LocalDate endDate, double numberOfDays, String reason, Double remainingLeave);
}
