package com.example.good.controllers;
import com.example.good.config.RedisConfig;
import com.example.good.services.GoodCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class GoodCartController {
    @Autowired
    GoodCartService goodCartService;

    /**
     * 查看购物车内容
     * @param uuid
     * @return
     */
    @GetMapping("/cart")
    public Map<String,String> getCart(@RequestHeader(value = "uuid")String uuid){
        return goodCartService.getGoodsInCart(uuid);
    }

    /**
     * 添加商品到购物车
     * @param uuid
     * @param map
     * @return
     */
    @PostMapping("/addGoodToCart")
    public boolean addGoodToCart(@RequestHeader(value = "uuid")String uuid, @RequestBody Map map){
        return goodCartService.addGoodToCart(uuid,(String)map.get("good_uuid"),(int)map.get("num"));
    }

    /**
     * 从购物车中删除商品
     * @param uuid
     * @param map
     * @return
     */
    @PostMapping("/deleteGood")
    public boolean deleteGood(@RequestHeader(value = "uuid")String uuid, @RequestBody Map map){
        return goodCartService.deleteGoodFromCart(uuid,(String)map.get("good_uuid"),(int)map.get("num"));
    }
}
