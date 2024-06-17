package com.lifecraft.todoappspringbootsrv.mapper;

import com.lifecraft.todoappspringbootsrv.domain.Token;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

@Mapper
public interface TokenMapper {

    Token create(@Param("token") String token, @Param("user_id") UUID userId);
    Token getTokenByToken(@Param("token") String token);
    void updateValidateById(@Param("id") UUID id);

}
