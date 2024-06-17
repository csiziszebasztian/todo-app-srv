package com.lifecraft.todoappspringbootsrv.service;

import com.lifecraft.todoappspringbootsrv.exception.MessagingOperationException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImp implements EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    @Async
    public void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplateName,
            String confirmationUrl,
            String activationCode,
            String subject
    ) {

        if (emailTemplateName == null) {
            emailTemplateName = EmailTemplateName.ACTIVATE_ACCOUNT;
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {

            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED,
                    StandardCharsets.UTF_8.name()
            );

            Map<String, Object> properties = new HashMap<>();
            properties.put("username", username);
            properties.put("confirmationUrl", confirmationUrl);
            properties.put("activation_code", activationCode);

            Context context = new Context();
            context.setVariables(properties);

            helper.setFrom("contact@lifecraft.com");
            helper.setTo(to);
            helper.setSubject(subject);

            String template = templateEngine.process(emailTemplateName.getName(), context);

            helper.setText(template, true);

        } catch (Exception e) {
            throw new MessagingOperationException("Messaging operation failed: " + e.getMessage());
        }

        mailSender.send(mimeMessage);
    }
}
