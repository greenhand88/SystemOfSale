package com.example.admin.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;

public interface GoodsMapper {
    @Insert("insert into goods(uuid,goodname,gooddescription,price,stock,date) values (" +
            "#{uuid},#{goodName},#{goodDescription},#{price},#{stock},#{date})")
    public void addGoods(String uuid, String goodName, String goodDescription, double price, int stock, LocalDate date);
}
