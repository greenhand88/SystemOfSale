package com.example.order.mappers;

import org.apache.ibatis.annotations.Select;

//只读
public interface GoodMapper {
    @Select("select price from goods where uuid=#{uuid}")
    public double getPrice(String uuid);
}
