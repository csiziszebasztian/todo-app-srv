package com.lifecraft.todoappspringbootsrv.service;


import com.lifecraft.todoappspringbootsrv.dto.AuthenticationRequestDto;
import com.lifecraft.todoappspringbootsrv.dto.AuthenticationResponseDto;
import com.lifecraft.todoappspringbootsrv.dto.SignUpRequestDto;

public interface AuthenticationService {

    void signUp(SignUpRequestDto request);
    AuthenticationResponseDto authentication(AuthenticationRequestDto request);
    void activateAccount(String tokenId);
}
