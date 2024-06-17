package com.lifecraft.todoappspringbootsrv.mapper;

import com.lifecraft.todoappspringbootsrv.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleMapper {

    Role getRoleByName(@Param("name") String roleName);

}
