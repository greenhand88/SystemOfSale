package com.example.user.services;

import com.example.user.VO.AccessVO;
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
    public UserInforResult getAllInfor(AccessVO accessVO){
        UserInfor userInfor = (UserInfor)redisTemplate.opsForValue().get(accessVO.getUuid());
        if(userInfor==null){
            return new UserInforResult("","","",LocalDate.parse("1980-01-01"),"","token失效!请重新登录");
        }
        else{
            amqpTemplate.convertAndSend(exchange,userInfroRouteKey,userInfor);
            UserContext allInfor = userInforMapper.getAllInfor(userInfor.getUuid());
            return new UserInforResult(userInfor.getUuid(),userInfor.getNickName(),userInfor.getPhoneNum(),allInfor.getLocalDate(),
                    allInfor.getAddress(),"成功获取所有个人信息");
        }

    }
}
