package com.example.admin.mappers;

import com.example.admin.utils.SPUInfor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
//SPU表
public interface SPUMapper {
    @Insert("insert into goods(spu,goodname,subtitle,date) values (" +
            "#{uuid},#{goodName},#{subtitle},#{date})")
    public void addSPU(String uuid, String goodName, String subtitle, LocalDateTime date);

    @Delete("delete from goods where spu=#{spu}")
    public void deleteGoods(String spu);

    @Select("select * from goods")
    public List<SPUInfor> getGoods();

}
