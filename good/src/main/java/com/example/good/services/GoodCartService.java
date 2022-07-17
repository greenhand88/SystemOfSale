package com.example.good.services;

import com.example.good.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class GoodCartService {
    @Autowired
    private RedisConfig redisConfig;//单例工厂类

    /**
     *
     * @param uuid
     * @param goodUuid
     * @param n
     * @return
     */
    public boolean addGoodToCart(String uuid,String goodUuid,int n){//先添加分布式锁
        try {
            if (redisConfig.getRedisTemplateByDb(6).opsForValue().setIfAbsent(uuid, uuid, 30, TimeUnit.SECONDS)) {
                //假意添加,即先不判断商品库存,结账时处理
                redisConfig.getRedisTemplateByDb(7).opsForHash().put(uuid,goodUuid,n);
                return true;
            }
            else
                return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            //释放分布式锁
            redisConfig.getRedisTemplateByDb(6).opsForValue().getAndDelete(uuid);
        }
    }

    /**
     * 删除一定数量购物车中的商品
     * @param uuid
     * @param goodUuid
     * @param n
     * @return
     */
    public boolean deleteGoodFromCart(String uuid,String goodUuid,int n){//先添加分布式锁
        try {
            if (redisConfig.getRedisTemplateByDb(6).opsForValue().setIfAbsent(uuid, uuid, 30, TimeUnit.SECONDS)) {
                //假意添加,即先不判断商品库存,结账时处理
                int k=(int)redisConfig.getRedisTemplateByDb(7).opsForHash().get(uuid,goodUuid);
                if(k>n){
                    redisConfig.getRedisTemplateByDb(7).opsForHash().put(uuid,goodUuid,k-n);
                }else{
                    redisConfig.getRedisTemplateByDb(7).opsForHash().delete(uuid,goodUuid);
                }
                return true;
            }
            else
                return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            //释放分布式锁
            redisConfig.getRedisTemplateByDb(6).opsForValue().getAndDelete(uuid);
        }
    }

    /**
     * 获取购物车中的商品
     * @param uuid
     * @return
     */
    public Map getGoodsInCart(String uuid){
        try{
            HashMap<String,String> map=(HashMap) redisConfig.getRedisTemplateByDb(7).opsForHash().entries(uuid);
            return map;
        }catch (Exception e){
            return new HashMap();
        }
    }
}
