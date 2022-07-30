package com.example.admin.controllers;

import com.example.admin.VO.PriceVO;
import com.example.admin.sevices.ImgService;
import com.example.admin.sevices.SKUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SKUController {
    @Autowired
    SKUService skuService;

    @Autowired
    ImgService imgService;

    @Autowired

    @PostMapping("/changePrice")
    public Boolean changePrice(@RequestBody PriceVO priceVO){
        return skuService.changePrice(priceVO.getSku(),priceVO.getPrice());
    }

    @PostMapping("/uploadImg")
    public boolean uploadImg(@RequestParam("sku")int sku, @RequestParam("file") MultipartFile file){
        return imgService.uploadImg(sku,file);
    }

    @GetMapping(value = "/getImg",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] uploadImg(@RequestParam("sku")int sku){
        return imgService.getImg(sku);
    }
}
