package com.example.admin.controllers;

import com.example.admin.VO.GoodVO;
import com.example.admin.VO.PriceVO;
import com.example.admin.sevices.GoodService;
import com.example.admin.utils.GoodInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/admin")
public class GoodController {
    @Autowired
    private GoodService goodService;

    @PostMapping("/addGood")
    public Boolean addGood(@RequestBody GoodVO goodVO){
        return goodService.addGoods(goodVO);
    }

    @PostMapping("/deleteGoods")
    public Boolean deleteGood(@RequestParam(value = "uuid")String uuid){
        return goodService.deleteGoods(uuid);
    }

    @GetMapping("/getGoods")
    public List<GoodInfor> getGoods(){
        return goodService.getGoods();
    }

    @PostMapping("/changePrice")
    public Boolean changePrice(@RequestBody PriceVO priceVO){
        return goodService.changePrice(priceVO);
    }
}
