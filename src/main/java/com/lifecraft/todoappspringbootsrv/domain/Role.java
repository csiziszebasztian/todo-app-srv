package com.lifecraft.todoappspringbootsrv.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    private UUID id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
