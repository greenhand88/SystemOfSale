package com.example.access.services;

import com.example.access.VO.UserRegisterVO;
import com.example.access.resultTemplate.LoginResult;
import com.example.access.tools.MD5Producer;
import com.example.access.tools.UUIDProducer;
import com.example.access.utils.UserRegisterInfor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterService {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Value("${mq.config.register.exchange}")
    private String exchange;
    @Value("${mq.config.register.routeKey}")
    private String routeKey;

    public LoginResult register(UserRegisterVO userRegisterVO) {
        String uuid = UUIDProducer.getUUID();
        String md5 = MD5Producer.getMD5String(userRegisterVO.getPassword());
        String salt=MD5Producer.getSalt();
        amqpTemplate.convertAndSend(exchange,routeKey,new UserRegisterInfor(
                uuid,
                userRegisterVO.getPhoneNum(),
                md5,
                userRegisterVO.getNickName(),
                salt
        ));
        return new LoginResult("",false,"注册成功!即将跳转到登录页面...");
    }

}
