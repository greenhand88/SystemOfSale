package com.example.admin.controllers;

import com.example.admin.VO.GoodVO;
import com.example.admin.VO.PriceVO;
import com.example.admin.sevices.GoodService;
import com.example.admin.sevices.ImgService;
import com.example.admin.utils.GoodInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
public class GoodController {
    @Autowired
    private GoodService goodService;
    @Autowired
    private ImgService imgService;

    @PostMapping("/addGood")
    public Boolean addGood(@RequestBody GoodVO goodVO){
        try{
            goodService.addGoods(goodVO);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @PostMapping("/deleteGoods")
    public Boolean deleteGood(@RequestParam(value = "uuid")String uuid){
        try{
            goodService.deleteGoods(uuid);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @GetMapping("/getGoods")
    public List<GoodInfor> getGoods(){
        return goodService.getGoods();
    }

    @PostMapping("/changePrice")
    public Boolean changePrice(@RequestBody PriceVO priceVO){
        return goodService.changePrice(priceVO);
    }

    @PostMapping("/uploadImg")
    public boolean uploadImg(@RequestParam("uuid")String uuid,@RequestParam("file") MultipartFile file){
        return imgService.uploadImg(uuid,file);
    }

    @GetMapping(value = "/getImg",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] uploadImg(@RequestParam("uuid")String uuid){
        return imgService.getImg(uuid);
    }
}
