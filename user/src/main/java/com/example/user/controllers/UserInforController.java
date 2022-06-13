package com.example.user.controllers;

import com.example.user.resultTemplate.UserInforResult;
import com.example.user.services.UserInforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInforController {
    @Autowired
    UserInforService userInforService;
    @PostMapping("/home")
    public UserInforResult getAllInfor(@RequestHeader(value = "uuid") String uuid){
        return userInforService.getAllInfor(uuid);
    }

}
