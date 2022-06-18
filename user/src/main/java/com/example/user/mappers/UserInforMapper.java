package com.example.user.mappers;

import com.example.user.utils.UserContext;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserInforMapper {
    @Select("select * from userinfor where uuid=#{uuid}")
    public UserContext getAllInfor(String uuid);
    @Insert("insert into userinfor(uuid,phonenum,nickname) values(#{uuid},#{phoneNum},#{nickName})")
    public void basicAdd(String uuid,String phoneNum,String nickName);
    @Insert("insert into userinfor(address) values(#{address}}) where uuid=#{uuid}")
    public void addAddress(String address,String uuid);
}
