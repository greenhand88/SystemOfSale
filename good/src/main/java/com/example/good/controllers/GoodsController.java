package com.example.good.controllers;

import com.example.good.services.GoodService;
import com.example.good.utils.GoodInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/good")
public class GoodsController {
    @Autowired
    private GoodService goodService;

    @GetMapping("/showgoods")
    public List<GoodInfor>getGoods(@RequestParam(value = "page")int page){
        return goodService.getGoods(page);
    }

    @PostMapping("/showgoods")
    public List<GoodInfor>getGoods(@RequestParam(value = "page")int page,@RequestParam(value = "page_size") int n){
        return goodService.getGoods(page,n);
    }

    @PostMapping("/search")
    public List<GoodInfor>searchGoods(@RequestParam(value = "good_name") String name){
        return goodService.searchGoods(name);
    }
}
