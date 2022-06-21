package com.example.admin.mappers;

import com.example.admin.utils.GoodInfor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

public interface GoodsMapper {
    @Insert("insert into goods(uuid,goodname,gooddescription,price,stock,date) values (" +
            "#{uuid},#{goodName},#{goodDescription},#{price},#{stock},#{date})")
    public void addGoods(String uuid, String goodName, String goodDescription, double price, int stock, LocalDate date);
    @Delete("delete from goods where uuid=#{uuid}")
    public void deleteGoods(String uuid);
    @Select("select * from goods")
    public List<GoodInfor> getGoods();

    @Update("update goods set price=#{price} where uuid=#{uuid}")
    public void changePrice(String uuid,double price);
}
