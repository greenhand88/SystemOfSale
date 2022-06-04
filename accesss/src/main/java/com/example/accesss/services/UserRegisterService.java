package com.example.accesss.services;

import com.example.accesss.VO.UserRegisterVO;
import com.example.accesss.mappers.UserMapper;
import com.example.accesss.resultTemplate.LoginResult;
import com.example.accesss.tools.MD5Producer;
import com.example.accesss.tools.UUIDProducer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class UserRegisterService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Value("${mq.config.reigister.exchange}")
    private String exchange;
    @Value("${mq.config.reigister.routeKey}")
    private String routeKey;

    public LoginResult register(UserRegisterVO userRegisterVO) {
        String uuid = UUIDProducer.getUUID();
        String md5String = MD5Producer.getMD5String(uuid);
        try {
            Field salt1 = MD5Producer.class.getDeclaredField("salt");
            salt1.setAccessible(true);
            String salt = (String)salt1.get(MD5Producer.class);
            userMapper.register(md5String,userRegisterVO.getPhoneNum(),userRegisterVO.getPasssword(),
                    userRegisterVO.getNickName(),salt);
            return new LoginResult("",false,"注册成功!即将跳转到登录页面...");
        }  catch (IllegalAccessException e) {
            e.printStackTrace();
            return new LoginResult("",false,"注册失败,请联系管理员!");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return new LoginResult("",false,"注册失败,请联系管理员!");
        }
    }

}
