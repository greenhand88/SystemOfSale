package com.example.access.services;

import com.example.access.mappers.UserMapper;
import com.example.access.utils.LastTime;
import com.example.access.utils.UserInfor;
import com.example.access.utils.UserPInfor;
import com.example.access.utils.UserRegisterInfor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
@Service
public class MQListenerService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private UserMapper userMapper;

    /**
     *
     * @param userInfor
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.access.queue}"),
            exchange = @Exchange("${mq.config.access.exchange}"),
            key="${mq.config.access.routeKey}"))
    public void tokenToRedis(UserInfor userInfor){
        redisTemplate.multi();
        redisTemplate.opsForHash().put(userInfor.getUuid(),"phoneNum",userInfor.getPhoneNum());
        redisTemplate.opsForHash().put(userInfor.getUuid(),"nickName",userInfor.getNickName());
        redisTemplate.opsForHash().put(userInfor.getUuid(),"password",userInfor.getPassword());
        redisTemplate.opsForHash().put(userInfor.getUuid(),"salt",userInfor.getSalt());
        redisTemplate.expire(userInfor.getUuid(),3,TimeUnit.HOURS);
        redisTemplate.exec();
    }

    /**
     * 刷新最近登录时间
     * @param lastTime
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.time.queue}"),
            exchange = @Exchange("${mq.config.time.exchange}"),
            key="${mq.config.time.routeKey}"))
    public void freshTime(LastTime lastTime){
        userMapper.record(lastTime.getUuid(),lastTime.getLocalDate());
    }
//    /**
//     * 热点数据延迟时间
//     * @param uuid
//     */
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue("${mq.config.fresh.queue}"),
//            exchange = @Exchange("${mq.config.fresh.exchange}"),
//            key="${mq.config.fresh.routeKey}"))
//    public void freshToken(String uuid){
//        redisTemplate.expire(uuid,3,TimeUnit.HOURS);
//    }

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
        userMapper.register(userRegisterInfor.getUuid(),
                userRegisterInfor.getPhoneNum(),
                userRegisterInfor.getMd5(),
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
