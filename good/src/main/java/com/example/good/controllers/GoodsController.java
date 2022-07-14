package com.example.good.controllers;

import com.example.good.services.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class GoodsController {
    @Autowired
    private GoodService goodService;


}
