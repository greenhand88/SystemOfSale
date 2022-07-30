package com.example.admin.mappers;

import com.example.admin.utils.SKUInfor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

//SKU表
public interface SKUMapper {
    @Insert("insert into gooddetail(spu,detail,img,price,stock) values " +
            "(#{spu},#{detail},#{img},#{price},#{stock})")
    public void addSKU(String spu,String detail,String img,double price,int stock);
    //select
    @Select("select img from gooddetail where sku=#{sku}")
    public String getImg(int sku);

    @Select("select detail from gooddetail where sku=#{sku}")
    public String getDetail(int sku);

    @Select("select * from gooddetail where sku=#{sku}")
    public SKUInfor getSKU(int sku);

    @Select("select detail from gooddetail where spu=#{spu}")
    public List<String> getAllDetailWithSpu(String spu);

    @Select("select * from gooddetail where spu=#{spu}")
    public List<SKUInfor> getAllSKUWithSpu(String spu);

    //update
    @Update("update gooddetail set detail=#{detail} where sku=#{sku}")
    public void updateDetail(int sku,String detail);

    @Update("update gooddetail set price=#{price} where sku=#{sku}")
    public void updatePrice(int sku,double price);

    @Update("update gooddetail set stock=#{stock} where sku=#{sku}")
    public void updateStock(int sku,int stock);

    @Update("update gooddetail set img=#{img} where sku=#{sku}")
    public void updateImage(int sku,String img);

    //delete
    @Delete("delete from gooddetail where sku=#{sku}")
    public void deleteSKU(int sku);

    @Delete("delete from gooddetail where spu=#{spu}")
    public void deleteSKUWithSPU(String spu);
}
