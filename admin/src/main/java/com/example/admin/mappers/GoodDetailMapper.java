package com.example.admin.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

public interface GoodDetailMapper {
    @Insert("insert into gooddetail(uuid) values (#{uuid})")
    public void addGoods(String uuid);

    @Update("update gooddetail set img=#{img} where uuid=#{uuid}")
    public void updateImage(String uuid,String img);

    @Update("update gooddetail set detail=#{detail} where uuid=#{uuid}")
    public void updateDetail(String uuid,String detail);

    @Delete("delete from gooddetail where uuid=#{uuid}")
    public void deleteGoods(String uuid);

    @Select("select img from gooddetail where uuid=#{uuid}")
    public String getImg(String uuid);

    @Select("select detail from gooddetail where uuid=#{uuid}")
    public String getdetail(String uuid);
}
