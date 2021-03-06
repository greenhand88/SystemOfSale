package com.example.good.controllers;

import com.example.good.services.GoodService;
import com.example.good.services.ImgService;
import com.example.good.utils.GoodInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/good")
public class GoodsController {
    @Autowired
    private GoodService goodService;

    @Autowired
    private ImgService imgService;
    @GetMapping("/showgoods")
    public List<GoodInfor>getGoods(@RequestParam(value = "page")int page){
        return goodService.getGoods(page);
    }

    @PostMapping("/showgoods")
    public List<GoodInfor>getGoods(@RequestParam(value = "page")int page,@RequestParam(value = "page_size") int n){
        return goodService.getGoods(page,n);
    }

    @PostMapping("/search")
    public List<GoodInfor>searchGoods(@RequestBody Map map){
        return goodService.searchGoods((String)map.get("good_name"));
    }

    @GetMapping(value = "/getImg",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImg(@RequestParam("uuid")String uuid){
        return imgService.getImg(uuid);
    }
}
