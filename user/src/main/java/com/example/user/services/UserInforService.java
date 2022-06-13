package com.example.user.services;

import com.example.user.mappers.UserInforMapper;
import com.example.user.resultTemplate.UserInforResult;
import com.example.user.utils.UserContext;
import com.example.user.utils.UserInfor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Map;


@Service
public class UserInforService {
    @Autowired
    UserInforMapper userInforMapper;
    @Autowired
    AmqpTemplate amqpTemplate;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Value("${mq.config.userInfor.exchange}")
    private String exchange;
    @Value("${mq.config.userInfor.routeKey}")
    private String userInfroRouteKey;
    public UserInforResult getAllInfor(String uuid){
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(uuid);
        UserInfor userInfor = new UserInfor(uuid,(String)entries.get("nickName"),
                (String)entries.get("phoneNum"),
                (String)entries.get("password"),
                (String)entries.get("salt"));
        if(userInfor==null){
            return new UserInforResult("","","",LocalDate.parse("1980-01-01"),"","token失效!请重新登录");
        }
        else{
            amqpTemplate.convertAndSend(exchange,userInfroRouteKey,userInfor);
            UserContext allInfor = userInforMapper.getAllInfor(userInfor.getUuid());
            if(allInfor==null)
                return new UserInforResult(userInfor.getUuid(),userInfor.getNickName(),userInfor.getPhoneNum(),LocalDate.parse("1980-01-01"),
                        "","成功获取所有个人信息");
            return new UserInforResult(userInfor.getUuid(),userInfor.getNickName(),userInfor.getPhoneNum(),allInfor.getLocalDate(),
                    allInfor.getAddress(),"成功获取所有个人信息");
        }

    }
}
