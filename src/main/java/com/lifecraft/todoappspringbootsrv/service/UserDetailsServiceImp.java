package com.lifecraft.todoappspringbootsrv.service;

import com.lifecraft.todoappspringbootsrv.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return userMapper.getByEmail(email);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found: " + email);
        }
    }

}
