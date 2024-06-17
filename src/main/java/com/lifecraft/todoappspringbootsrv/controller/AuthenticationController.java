package com.lifecraft.todoappspringbootsrv.controller;

import com.lifecraft.todoappspringbootsrv.dto.AuthenticationRequestDto;
import com.lifecraft.todoappspringbootsrv.dto.AuthenticationResponseDto;
import com.lifecraft.todoappspringbootsrv.dto.SignUpRequestDto;
import com.lifecraft.todoappspringbootsrv.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signUp(
            @RequestBody @Valid SignUpRequestDto request
    ) {
        service.signUp(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody AuthenticationRequestDto request
    ) {
        AuthenticationResponseDto response = service.authentication(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/activate-account")
    public void confirm(
            @RequestParam String token
    ) {
        service.activateAccount(token);
    }

}
