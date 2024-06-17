package com.lifecraft.todoappspringbootsrv.service;

import com.lifecraft.todoappspringbootsrv.domain.Role;
import com.lifecraft.todoappspringbootsrv.domain.Token;
import com.lifecraft.todoappspringbootsrv.domain.User;
import com.lifecraft.todoappspringbootsrv.dto.AuthenticationRequestDto;
import com.lifecraft.todoappspringbootsrv.dto.AuthenticationResponseDto;
import com.lifecraft.todoappspringbootsrv.dto.SignUpRequestDto;
import com.lifecraft.todoappspringbootsrv.exception.DBOperationException;
import com.lifecraft.todoappspringbootsrv.exception.ResourceNotFoundException;
import com.lifecraft.todoappspringbootsrv.exception.TokenExpiredException;
import com.lifecraft.todoappspringbootsrv.mapper.RoleMapper;
import com.lifecraft.todoappspringbootsrv.mapper.TokenMapper;
import com.lifecraft.todoappspringbootsrv.mapper.UserMapper;
import com.lifecraft.todoappspringbootsrv.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;
    private final TokenMapper tokenMapper;
    private final EmailService emailService;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    @Override
    public void signUp(SignUpRequestDto request) {

        User user = null;
        Role role = null;

        try {
            user = userMapper.save(
                    request.getName(),
                    request.getEmail(),
                    passwordEncoder.encode(request.getPassword())
            );
        } catch (Exception e) {
            throw new DBOperationException("User creation failed: " + e.getMessage());
        }

        if (user == null) {
            throw new ResourceNotFoundException("User not found: " + request.getEmail());
        }

        try {
            role = roleMapper.getRoleByName("USER");
        } catch (Exception e) {
            throw new DBOperationException("Fetch role failed: " + e.getMessage());
        }

        if (role == null) {
            throw new ResourceNotFoundException("Role not found: USER");
        }

        try {
            userRoleMapper.save(user.getId(), role.getId());
        } catch (Exception e) {
            throw new DBOperationException("UserRole creation failed: " + e.getMessage());
        }

        sendValidationEmail(user);
    }

    @Override
    public AuthenticationResponseDto authentication(AuthenticationRequestDto request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        HashMap<String, Object> claims = new HashMap<>();
        User user = ((User) auth.getPrincipal());

        claims.put("Name", user.getName());

        String jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());

        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public void activateAccount(String token) {

        Token savedToken = null;

        try {
            savedToken = tokenMapper.getTokenByToken(token);
        } catch (Exception e) {
            throw new DBOperationException("Token read failed: " + e.getMessage());
        }

        if(savedToken == null) {
            throw new ResourceNotFoundException("Token not found: " + token);
        }

        if (LocalDateTime.now().isAfter(savedToken.getExpiredAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new TokenExpiredException("Activation token has expired. A new token has been send to the same email address");
        }

        try {
            userMapper.updateEnableById(savedToken.getUser().getId());
        } catch (Exception e) {
            throw new DBOperationException("User enabled failed: " + e.getMessage());
        }

        try{
            tokenMapper.updateValidateById(savedToken.getId());
        } catch (Exception e) {
            throw new DBOperationException("Token validation failed: " + e.getMessage());
        }

    }

    private String generateAndSaveActivationToken(User user) {

        String generatedToken = generateActivationCode(6);

        try {
            tokenMapper.create(generatedToken, user.getId());
        } catch (Exception e) {
            throw new DBOperationException("Token creation failed: " + e.getMessage());
        }

        return generatedToken;
    }

    private void sendValidationEmail(User user) {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.getName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }

}
