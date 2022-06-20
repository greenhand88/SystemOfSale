package com.example.admin.sevices;

import com.example.admin.VO.GoodVO;
import com.example.admin.mappers.GoodsMapper;
import com.example.admin.tools.UUIDProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GoodService {
    /*
    低并发,直接用数据库,减少程序复杂度
     */
    @Autowired
    private GoodsMapper goodsMapper;

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

}
