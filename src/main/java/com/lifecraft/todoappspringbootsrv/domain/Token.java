package com.lifecraft.todoappspringbootsrv.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    private UUID id;
    private String tokenStr;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime validatedAt;
    private User user;
}
