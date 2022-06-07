package com.example.gateway.services;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
@Service
public class MQService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    /**
     * 热点数据延迟时间
     * @param uuid
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.fresh.queue}"),
            exchange = @Exchange("${mq.config.fresh.exchange}"),
            key="${mq.config.fresh.routeKey}"))
    public void freshToken(String uuid){
        redisTemplate.expire(uuid,3, TimeUnit.HOURS);
    }
}
