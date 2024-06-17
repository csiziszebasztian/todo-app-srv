package com.lifecraft.todoappspringbootsrv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

@Mapper
public interface UserRoleMapper {

    void save(
            @Param("user_id") UUID userId,
            @Param("role_id") UUID roleId
    );

}
