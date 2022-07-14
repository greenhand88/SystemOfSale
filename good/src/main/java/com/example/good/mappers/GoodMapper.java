package com.example.good.mappers;

import com.example.good.utils.GoodInfor;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodMapper {
    @Select("select * from goods order by limit 10")
    public List<GoodInfor> getGoods();
    @Select("select * from goods limit #{n}")
    public List<GoodInfor> getGoods(int n);

}
