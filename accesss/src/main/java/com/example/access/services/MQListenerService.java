package com.example.access.services;

import com.example.access.mappers.UserMapper;
import com.example.access.utils.UserInfor;
import com.example.access.utils.UserPInfor;
import com.example.access.utils.UserRegisterInfor;
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

    @Autowired
    private UserMapper userMapper;
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

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.register.queue}"),
            exchange = @Exchange("${mq.config.register.exchange}"),
            key="${mq.config.register.routeKey}"))
    public void register(UserRegisterInfor userRegisterInfor){
        userMapper.register(userRegisterInfor.getMd5(),
                userRegisterInfor.getPhoneNum(),
                userRegisterInfor.getPasssword(),
                userRegisterInfor.getNickName(),
                userRegisterInfor.getSalt()
                );
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.changePass.queue}"),
            exchange = @Exchange("${mq.config.changePass.exchange}"),
            key="${mq.config.changePass.routeKey}"))
    public void changeP(UserPInfor userPInfor){
        userMapper.changePassword(userPInfor.getUuid(), userPInfor.getPassword());
    }
}
