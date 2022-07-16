package com.example.admin.controllers;

import com.example.admin.sevices.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping("/login")
    public String login(@RequestParam("account")String accunt,@RequestParam("password")String password){
        return loginService.veritify(accunt,password);
    }
}
