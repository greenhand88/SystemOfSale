package com.example.good.services;

import com.example.good.tools.UUIDProducer;
import com.example.good.utils.OrderInfor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MQService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${mq.config.order.exchange}")
    private String exchange;
    @Value("${mq.config.order.routeKey}")
    private String routeKey;

    public void sendOrderInfor(String uuid,Map<String,Integer> map){
        String orderUuid= UUIDProducer.getUUID();
        amqpTemplate.convertAndSend(exchange,routeKey,new OrderInfor(map,uuid,orderUuid));
    }
}
