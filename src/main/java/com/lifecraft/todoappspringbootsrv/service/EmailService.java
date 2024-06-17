package com.lifecraft.todoappspringbootsrv.service;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    @Async
    void sendEmail(String to,
                   String username,
                   EmailTemplateName emailTemplateName,
                   String confirmationUrl,
                   String activationCode,
                   String subject);

}
