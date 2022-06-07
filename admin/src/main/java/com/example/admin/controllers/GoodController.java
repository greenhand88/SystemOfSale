package com.example.admin.controllers;

import com.example.admin.sevices.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/admin")
public class GoodController {
    @Autowired
    GoodService goodService;
    @GetMapping("/addGood")
    public void addGood(){

    }
}
