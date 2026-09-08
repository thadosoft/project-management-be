package com.example.projectmanagementbe.api.services.impls.mail;

import com.example.projectmanagementbe.api.services.mail.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    /** Company logo bundled at src/main/resources/images/logo.png, embedded via CID. */
    private static final String LOGO_RESOURCE = "images/logo.png";
    private static final String LOGO_CID = "companyLogo";

    private final JavaMailSender mailSender;

    /** Set true khi đã valid SMTP credentials are configured. */
    @Value("${app.mail.enabled:true}")
    private boolean mailEnabled;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend.url:https://project-mana-fe.pages.dev}")
    private String frontendUrl;

    @Override
    public void sendProjectCreationNotification(List<String> toEmails, String projectName) throws MessagingException {
        for (String to : toEmails) {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("Dự án mới: " + projectName);
            helper.setText("<p>Bạn đã được thêm vào dự án: <strong>" + projectName + "</strong></p>", true);
//            mailSender.send(message);
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

//        mailSender.send(message);
    }

    @Override
    public void sendLeaveRequestNotification(List<String> toEmails, String employeeName,
        LocalDate startDate, LocalDate endDate, double numberOfDays, String reason, Double remainingLeave) {
        if (toEmails == null || toEmails.isEmpty()) {
            return;
        }
        if (!mailEnabled) {
            log.info("Mail disabled (app.mail.enabled=false); skipped leave request email to {}", toEmails);
            return;
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmails.toArray(new String[0]));
            helper.setSubject("Đơn xin nghỉ phép mới từ " + employeeName);

            String content = buildLeaveRequestHtml(
                employeeName, startDate, endDate, numberOfDays, reason, remainingLeave);
            helper.setText(content, true);

            ClassPathResource logo = new ClassPathResource(LOGO_RESOURCE);
            if (logo.exists()) {
                helper.addInline(LOGO_CID, logo);
            } else {
                log.warn("Logo resource {} not found on classpath; email sent without logo", LOGO_RESOURCE);
            }

            log.info("Sending leave request email from {} to {}", fromEmail, toEmails);
            mailSender.send(message);
            log.info("Leave request notification email sent successfully to {}", toEmails);
        } catch (Exception ex) {
            log.error("Failed to send leave request notification email", ex);
        }
    }

    private String buildLeaveRequestHtml(String employeeName, LocalDate startDate, LocalDate endDate,
        double numberOfDays, String reason, Double remainingLeave) {
        String days = formatNumber(numberOfDays);
        String reviewUrl = frontendUrl.replaceAll("/+$", "") + "/leave-approval";

        String balanceRows;
        if (remainingLeave == null) {
            balanceRows = """
                      <tr><td style="padding:8px 0;color:#64748b;">Phép còn lại</td><td style="padding:8px 0;">-</td></tr>
                """;
        } else {
            double afterApproval = remainingLeave - numberOfDays;
            String afterColor = afterApproval < 0 ? "#dc2626" : "#0f172a";
            String afterSuffix = afterApproval < 0 ? " (âm phép)" : "";
            balanceRows = """
                      <tr><td style="padding:8px 0;color:#64748b;">Phép còn lại</td><td style="padding:8px 0;">%s ngày</td></tr>
                      <tr><td style="padding:8px 0;color:#64748b;">Còn lại sau khi duyệt</td><td style="padding:8px 0;color:%s;font-weight:bold;">%s ngày%s</td></tr>
                """.formatted(formatNumber(remainingLeave), afterColor, formatNumber(afterApproval), afterSuffix);
        }

        return """
            <div style="margin:0;padding:24px;background:#f1f5f9;font-family:Arial,Helvetica,sans-serif;">
              <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" style="max-width:560px;margin:0 auto;background:#ffffff;border-radius:12px;overflow:hidden;border:1px solid #e2e8f0;">
                <tr>
                  <td style="background:#0f172a;padding:20px 28px;text-align:center;">
                    <img src="cid:%s" alt="Thadosoft" width="120" style="display:block;margin:0 auto;max-width:120px;height:auto;" />
                  </td>
                </tr>
                <tr>
                  <td style="padding:28px;">
                    <h2 style="margin:0 0 6px;font-size:18px;color:#0f172a;">Đơn xin nghỉ phép đang chờ duyệt</h2>
                    <p style="margin:0 0 20px;color:#475569;font-size:14px;">Một nhân viên vừa gửi đơn xin nghỉ phép cần bạn xử lý.</p>
                    <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" style="font-size:14px;color:#0f172a;">
                      <tr><td style="padding:8px 0;color:#64748b;width:150px;">Nhân viên</td><td style="padding:8px 0;font-weight:bold;">%s</td></tr>
                      <tr><td style="padding:8px 0;color:#64748b;">Từ ngày</td><td style="padding:8px 0;">%s</td></tr>
                      <tr><td style="padding:8px 0;color:#64748b;">Đến ngày</td><td style="padding:8px 0;">%s</td></tr>
                      <tr><td style="padding:8px 0;color:#64748b;">Số ngày nghỉ</td><td style="padding:8px 0;">%s ngày</td></tr>
                %s
                      <tr><td style="padding:8px 0;color:#64748b;vertical-align:top;">Lý do</td><td style="padding:8px 0;">%s</td></tr>
                    </table>
                    <div style="text-align:center;margin:28px 0 4px;">
                      <a href="%s" style="display:inline-block;background:#2563eb;color:#ffffff;text-decoration:none;font-size:14px;font-weight:bold;padding:12px 28px;border-radius:8px;">Xem &amp; duyệt đơn</a>
                    </div>
                    <p style="margin:14px 0 0;color:#94a3b8;font-size:12px;text-align:center;word-break:break-all;">%s</p>
                  </td>
                </tr>
                <tr>
                  <td style="background:#f8fafc;padding:16px 28px;text-align:center;color:#94a3b8;font-size:12px;border-top:1px solid #e2e8f0;">
                    Email tự động từ Hệ thống Quản lý Dự án — vui lòng không trả lời email này.
                  </td>
                </tr>
              </table>
            </div>
            """.formatted(
                LOGO_CID,
                escapeHtml(employeeName),
                startDate,
                endDate,
                days,
                balanceRows,
                reason == null || reason.isBlank() ? "-" : escapeHtml(reason),
                reviewUrl,
                reviewUrl);
    }

    private static String formatNumber(double value) {
        return value == Math.rint(value) ? String.valueOf((long) value) : String.valueOf(value);
    }

    private static String escapeHtml(String raw) {
        if (raw == null) {
            return "";
        }
        return raw.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;");
    }
}