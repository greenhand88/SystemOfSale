package com.example.admin.sevices;

import com.example.admin.VO.GoodVO;
import com.example.admin.VO.PriceVO;
import com.example.admin.mappers.GoodsMapper;
import com.example.admin.tools.UUIDProducer;
import com.example.admin.utils.GoodInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodService {
    /*
    低并发,直接用数据库,减少程序复杂度
     */
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 上架商品
     * @param goodVO
     * @return
     */
    public boolean addGoods(GoodVO goodVO){
        try{
            goodsMapper.addGoods(UUIDProducer.getUUID(), goodVO.getGoodName(),
                    goodVO.getGoodDescription(), goodVO.getPrice(), goodVO.getStock(), LocalDate.now());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 下架商品
     * @param uuid
     * @return
     */
    public boolean deleteGoods(String uuid){
        try{
            goodsMapper.deleteGoods(uuid);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @return
     */
    public List<GoodInfor> getGoods(){
        try{
            return goodsMapper.getGoods();
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param priceVO
     * @return
     */
    public boolean changePrice(PriceVO priceVO){
        try{
            goodsMapper.changePrice(priceVO.getUuid(), priceVO.getPrice());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
