package com.example.admin.sevices;

import com.example.admin.VO.GoodVO;
import com.example.admin.VO.PriceVO;
import com.example.admin.mappers.GoodDetailMapper;
import com.example.admin.mappers.GoodsMapper;
import com.example.admin.tools.UUIDProducer;
import com.example.admin.utils.GoodInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodService {
    /*
    低并发,直接用数据库,减少程序复杂度
     */
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodDetailMapper goodDetailMapper;

    /**
     * 上架商品
     * @param goodVO
     * @return
     */
    @Transactional
    public void addGoods(GoodVO goodVO) throws Exception{
        String uuid = UUIDProducer.getUUID();
        //数据库外键约束改成CASCADE
        goodsMapper.addGoods(uuid, goodVO.getGoodName(),
                goodVO.getGoodDescription(), goodVO.getPrice(), goodVO.getStock(), LocalDateTime.now());
        goodDetailMapper.addGoods(uuid);
    }
    /**
     * 下架商品
     * @param uuid
     * @return
     */
    @Transactional
    public void deleteGoods(String uuid)throws Exception{
        //数据库外键约束改成CASCADE
        //goodDetailMapper.deleteGoods(uuid);//先删除从表数据
        goodsMapper.deleteGoods(uuid);
    }

    /**
     * 获取商品简讯
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
     * 修改价格
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

    /**
     * 修改商品详情
     * @param uuid
     * @param detail
     * @return
     */
    public boolean updateDetail(String uuid,String detail){
        try {
            goodDetailMapper.updateDetail(uuid,detail);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取商品详情
     * @param uuid
     * @return
     */
    public String getDetail(String uuid){
        try {
            return goodDetailMapper.getdetail(uuid);
        }catch (Exception e){
            e.printStackTrace();
            return "服务端出现异常!";
        }
    }
}
