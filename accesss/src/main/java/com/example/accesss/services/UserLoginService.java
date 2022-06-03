package com.example.accesss.services;


import com.example.accesss.VO.UserLoginVO;
import com.example.accesss.mappers.UserMapper;
import com.example.accesss.resultTemplate.LoginResult;
import com.example.accesss.tools.JWTProducer;
import com.example.accesss.utils.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.AmqpTemplate;
@Service
public class UserLoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate <String,Object>redisTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;
    public LoginResult loginByPassword(UserLoginVO userLoginVO){
        UserInfor userInfor = userMapper.getInforByPhoneNum(userLoginVO.getPhonenum());
        if(userInfor==null)
            return new LoginResult("",false,"null");
        if(userInfor.getPassword().equals(userLoginVO.getPassword())){
            String token = JWTProducer.getToken(userInfor.getUuid());
            //todo:amqpTemplate.convertAndSend();
            return new LoginResult(token,true,"登录成功!");
        }else{
            return new LoginResult("",false,"密码错误!");
        }
    }
}
