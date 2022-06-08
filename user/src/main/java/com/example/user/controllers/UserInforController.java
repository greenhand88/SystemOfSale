package com.example.user.controllers;

import com.example.user.VO.AccessVO;
import com.example.user.resultTemplate.UserInforResult;
import com.example.user.resultTemplate.UserResult;
import com.example.user.services.UserInforService;
import com.example.user.utils.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInforController {
    @Autowired
    UserInforService userInforService;
    @PostMapping("/home")
    public UserInforResult getAllInfor(@RequestHeader AccessVO accessVO){
        return userInforService.getAllInfor(accessVO);
    }
}
