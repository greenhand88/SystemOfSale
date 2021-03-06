package com.example.good.mappers;

import com.example.good.utils.GoodInfor;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.List;

public interface GoodMapper {
    @Select("select * from goods limit #{start},10")
    public List<GoodInfor> getGoods(int start);

    @Select("select * from goods limit 1,#{n}")
    public List<GoodInfor> getGoodsLimitN(int start, int n);

    @Select("select * from goods where goodname like #{pattern}")
    public List<GoodInfor> searchForGood(String pattern);

    @Select("select img from gooddetail where uuid=#{uuid}")
    public String getImg(String uuid);

    @Select("select detail from gooddetail where uuid=#{uuid}")
    public String getdetail(String uuid);

    @Select("select stock from goods where uuid=#{uuid}")
    public int getStock(String uuid);

    @Select("select goodname from goods where uuid=#{uuid}")
    public String getGoodName(String uuid);

    @Update("update goods set stock=stock-#{n}where uuid=#{uuid}")
    public void decreaseStock(String uuid,int n);
}
