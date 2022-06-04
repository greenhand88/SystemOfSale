package com.example.accesss.controllers;

import com.example.accesss.VO.TokenVO;
import com.example.accesss.VO.UserLoginVO;
import com.example.accesss.VO.UserRegisterVO;
import com.example.accesss.resultTemplate.LoginResult;
import com.example.accesss.services.UserLoginService;
import com.example.accesss.services.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserAccess {
    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserRegisterService userRegisterService;
    @PostMapping("/login")
    public LoginResult userLogin(@RequestBody UserLoginVO userLoginVO){
        return userLoginService.loginByPassword(userLoginVO);
    }

    @PostMapping("/loginByToken")
    public LoginResult userLogin(@RequestBody TokenVO tokenVO){
        return userLoginService.loginByToken(tokenVO);
    }

    @PostMapping("/register")
    public LoginResult register(@RequestBody UserRegisterVO userRegisterVO){
        return userRegisterService.register(userRegisterVO);
    }

}
