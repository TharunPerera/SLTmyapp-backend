package com.example.Gate_pass_system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // true for HTML content

        mailSender.send(message);
    }

    @Async
    public void sendRejectionEmail(String to, String requestId, String role, String comments, String status) throws MessagingException {
        String subject = "Gate Pass Request #" + requestId + " - " + status + " by " + role;
        String body = "<h3>Gate Pass Request Status Update</h3>" +
                "<p>Your gate pass request (Ref No: " + requestId + ") has been " + status.toLowerCase() + " by the " + role + ".</p>" +
                "<p><strong>Comments:</strong> " + (comments != null ? comments : "No comments provided") + "</p>" +
                "<p>Please review the details in the Gate Pass System.</p>";

        sendEmail(to, subject, body);
    }

    @Async
    public void sendApprovalEmail(String to, String requestId, String role, String comments) throws MessagingException {
        String subject = "Gate Pass Request #" + requestId + " - Approved by " + role;
        String body = "<h3>Gate Pass Request Status Update</h3>" +
                "<p>Your gate pass request (Ref No: " + requestId + ") has been approved by the " + role + ".</p>" +
                "<p><strong>Comments:</strong> " + (comments != null ? comments : "No comments provided") + "</p>" +
                "<p>Please review the details in the Gate Pass System.</p>";

        sendEmail(to, subject, body);
    }
}