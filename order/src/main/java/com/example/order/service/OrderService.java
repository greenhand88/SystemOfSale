package com.example.order.service;

import com.example.order.mappers.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    //todo: 和订单相关的操作
    @Autowired
    private OrderMapper orderMapper;


}
