package com.example.accesss.controllers;

import com.example.accesss.VO.UserLoginVO;
import com.example.accesss.resultTemplate.LoginResult;
import com.example.accesss.services.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserLogin {
    @Autowired
    UserLoginService userLoginService;

    @PostMapping("/login")
    public LoginResult userLogin(@RequestBody UserLoginVO userLoginVO){
        return null;
    }


}
