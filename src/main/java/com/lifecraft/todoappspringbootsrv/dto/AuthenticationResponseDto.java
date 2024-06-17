package com.lifecraft.todoappspringbootsrv.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponseDto {
    private String token;
}
