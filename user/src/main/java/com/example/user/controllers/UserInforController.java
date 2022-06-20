package com.example.user.controllers;

import com.example.user.resultTemplate.UserInforResult;
import com.example.user.services.UserInforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInforController {
    @Autowired
    private UserInforService userInforService;
    @PostMapping("/home")
    public UserInforResult getAllInfor(@RequestHeader(value = "uuid") String uuid){
        return userInforService.getAllInfor(uuid);
    }
    @PostMapping("/addAddress")
    public boolean addAddress(@RequestHeader(value = "uuid") String uuid, @RequestParam(value = "address") String address){
        return userInforService.addAddress(uuid,address);
    }

}
