package com.example.admin.sevices;

import com.example.admin.mappers.SKUMapper;
import com.example.admin.utils.SKUInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SKUService {
    @Autowired
    private SKUMapper skuMapper;

    /**
     * 修改商品价格
     * @param sku
     * @param price
     * @return
     */
    public boolean changePrice(int sku,double price){
        try{
            skuMapper.updatePrice(sku,price);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param sku
     * @param stock
     * @return
     */
    public boolean changeStock(int sku,int stock){
        try{
            skuMapper.updatePrice(sku,stock);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改商品详情
     * @param sku
     * @param detail
     * @return
     */
    public boolean updateDetail(int sku,String detail){
        try {
            skuMapper.updateDetail(sku,detail);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取所有配置的商品
     * @param spu
     * @return
     */
    public List<SKUInfor> getSKUWithSPU(String spu){
        try {
            return skuMapper.getAllSKUWithSpu(spu);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 获取某种商品所有信息
     * @param sku
     * @return
     */
    public SKUInfor getSKU(int sku){
        try {
            return skuMapper.getSKU(sku);
        }catch (Exception e){
            e.printStackTrace();
            return new SKUInfor();
        }
    }

    /**
     * 获取商品配置详情
     * @param sku
     * @return
     */
    public String getDetail(int sku){
        try {
            return skuMapper.getDetail(sku);
        }catch (Exception e){
            e.printStackTrace();
            return "服务端出现异常!";
        }
    }

    /**
     * 获取商品的所有配置详情
     * @param spu
     * @return
     */
    public List<String> getAllDetailWithSPU(String spu){
        try {
            return skuMapper.getAllDetailWithSpu(spu);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
