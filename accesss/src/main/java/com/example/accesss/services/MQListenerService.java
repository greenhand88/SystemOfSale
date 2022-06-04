package com.example.accesss.services;

import com.example.accesss.utils.UserInfor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class MQListenerService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.access.queue}"),
            exchange = @Exchange("${mq.config.access.exchange}"),
            key="${mq.config.access.routeKey}"))
    public void tokenToRedis(UserInfor userInfor){
        redisTemplate.opsForValue().set(userInfor.getUuid(),userInfor,3, TimeUnit.HOURS);
    }

    /**
     * 热点数据延迟时间
     * @param uuid
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.fresh.queue}"),
            exchange = @Exchange("${mq.config.fresh.exchange}"),
            key="${mq.config.fresh.routeKey}"))
    public void freshToken(String uuid){
        redisTemplate.expire(uuid,3,TimeUnit.HOURS);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.logOut.queue}"),
            exchange = @Exchange("${mq.config.logOut.exchange}"),
            key="${mq.config.logOut.routeKey}"))
    public void removeToken(String uuid){
        redisTemplate.delete(uuid);
    }
}
