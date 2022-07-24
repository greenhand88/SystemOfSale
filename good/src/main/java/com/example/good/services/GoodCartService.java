package com.example.good.services;

import com.example.good.mappers.GoodMapper;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class GoodCartService {
    @Autowired
    private RedisTemplate <String,Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private GoodService goodService;
    /**
     *
     * @param uuid
     * @param goodUuid
     * @param n
     * @return
     */
    public boolean addGoodToCart(String uuid,String goodUuid,int n){
        RLock lock = redissonClient.getLock(uuid);
        boolean success=false;
        try {
            //最多等待3s,给予业务最长处理事件为30s
            success = lock.tryLock(3,30, TimeUnit.SECONDS);//先添加分布式锁
            if (success) {
                //假意添加,即先不判断商品库存,结账时处理
                redisTemplate.opsForHash().put(uuid,goodUuid,n);
                return true;
            }
            else
                return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            //如果是本线程加的锁,那么释放分布式锁
            if(success)
                lock.unlock();
        }
    }

    /**
     * 删除一定数量购物车中的商品
     * @param uuid
     * @param goodUuid
     * @param n
     * @return
     */
    public boolean deleteGoodFromCart(String uuid,String goodUuid,int n){
        RLock lock = redissonClient.getLock(uuid);
        boolean success=false;
        try {
            success = lock.tryLock(3,30, TimeUnit.SECONDS);//先添加分布式锁
            if (success) {
                int k=(int)redisTemplate.opsForHash().get(uuid,goodUuid);
                if(k>n){
                    redisTemplate.opsForHash().put(uuid,goodUuid,k-n);
                }else{
                    redisTemplate.opsForHash().delete(uuid,goodUuid);
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
            if(success)
                lock.unlock();
        }
    }

    /**
     * 获取购物车中的商品
     * @param uuid
     * @return
     */
    public Map getGoodsInCart(String uuid){
        RLock lock = redissonClient.getLock(uuid);
        boolean success=false;
        try{
            success = lock.tryLock(3,30, TimeUnit.SECONDS);
            HashMap<String,String> map=(HashMap) redisTemplate.opsForHash().entries(uuid);
            return map;
        }catch (Exception e){
            return new HashMap();
        }finally {
            //释放分布式锁
            if(success)
                lock.unlock();
        }
    }

    /**
     *
     * @param uuid
     * @return
     */
    public String checkOut(String uuid){
        //由于MVCC导致事务间隔离,读的是快照读,因此选择将所有商品都加上分布式锁
        RLock lock = redissonClient.getLock(uuid);
        boolean success=false;
        HashMap<RLock,Boolean>map=new HashMap<>();
        try{
            success = lock.tryLock(3,60, TimeUnit.SECONDS);
            map.put(lock,success);
            Map<String, Integer> goodsInCart = getGoodsInCart(uuid);
            if (goodsInCart.isEmpty()) {
                return "购物车为空!";
            }
            boolean enough=false;
            for (String good_uuid : goodsInCart.keySet()) {
                enough=false;
                RLock lock1 = redissonClient.getLock(good_uuid);
                enough=lock1.tryLock(3,10,TimeUnit.SECONDS);
                map.put(lock1,enough);
                if(enough){
                    if (goodsInCart.get(good_uuid) > goodMapper.getStock(uuid)) {
                        return goodMapper.getGoodName(good_uuid)+"库存不足";
                    }
                }
                else{//没获取到某个商品的锁
                    return "服务器过忙,请稍后再试!";
                }
            }
            goodService.decreaseStock(goodsInCart);//保证事务不会失效
            //todo:给消息队列发送订单信息
            return "下单成功!";
        }catch (Exception e){
            e.printStackTrace();
            return "出现异常,请联系管理员!";
        }finally {
            for(RLock i:map.keySet()){
                if(map.get(i))
                    i.unlock();
            }
        }
    }

}
