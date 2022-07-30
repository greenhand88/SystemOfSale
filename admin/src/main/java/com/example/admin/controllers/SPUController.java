package com.example.admin.controllers;

import com.example.admin.VO.SPUVO;
import com.example.admin.sevices.SPUService;
import com.example.admin.sevices.ImgService;
import com.example.admin.utils.SPUInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class SPUController {
    @Autowired
    private SPUService spuService;
    @Autowired
    private ImgService imgService;

    @PostMapping("/addSPU")
    public Boolean addGood(@RequestBody SPUVO spuvo){
        try{
            spuService.addSPU(spuvo);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @PostMapping("/deleteSPU")
    public Boolean deleteSPU(@RequestParam(value = "uuid")String uuid){
        try{
            spuService.deleteSPU(uuid);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @GetMapping("/getSPUs")
    public List<SPUInfor> getSPUs(){
        return spuService.getSPUs();
    }


}
