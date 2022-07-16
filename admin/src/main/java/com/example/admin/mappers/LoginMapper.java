package com.example.admin.mappers;

import org.apache.ibatis.annotations.Select;

public interface LoginMapper {
    @Select("select password from admin where account=#{account}")
    public String getPassword(String accout);
}
