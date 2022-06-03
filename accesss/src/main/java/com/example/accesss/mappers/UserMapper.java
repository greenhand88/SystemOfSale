package com.example.accesss.mappers;

import com.example.accesss.utils.UserInfor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface UserMapper {
    @Select("select * from user where phonenum=#{phoneNum}")
    public UserInfor getInforByPhoneNum(String phoneNum);

    @Insert("insert into user(uuid,phonenum,password,nickname,salt) values(#{uuid},#{phoneNum},#{password},#{nickName},#{salt})")
    public void register(String uuid,String phoneNum,String password,String nickName,String salt);

    @Update("update user set password=#{newPassword} where uuid=#{uuid}")
    public void changePassword(String uuid,String newPassword);

}
