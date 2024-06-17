package com.lifecraft.todoappspringbootsrv.mapper;

import com.lifecraft.todoappspringbootsrv.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

@Mapper
public interface UserMapper {

    User save(
            @Param("name") String name,
            @Param("email") String email,
            @Param("password") String password
    );

    User getByEmail(@Param("email") String email);

    void updateEnableById(@Param("id") UUID id);

}
