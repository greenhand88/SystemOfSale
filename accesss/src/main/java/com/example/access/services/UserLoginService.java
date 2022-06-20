package com.example.access.services;


import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.access.VO.TokenVO;
import com.example.access.VO.UserLoginVO;
import com.example.access.mappers.UserMapper;
import com.example.access.resultTemplate.LoginResult;
import com.example.access.tools.JWTProducer;
import com.example.access.tools.MD5Producer;
import com.example.access.tools.RSAProducer;
import com.example.access.utils.LastTime;
import com.example.access.utils.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.AmqpTemplate;

import java.time.LocalDate;


@Service
public class UserLoginService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate <String,Object>redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Value("${mq.config.access.exchange}")
    private String exchange;
    @Value("${mq.config.access.routeKey}")
    private String routeKey;
    @Value("${mq.config.logOut.routeKey}")
    private String logOutRouteKey;
    @Value("${mq.config.time.routeKey}")
    private String timeRouteKey;
    /**
     * 获取公钥
     * @return
     */
    public String getPublicKey() {
        try{
            RSAProducer instance = RSAProducer.getInstance();
            return instance.getMypublicKey();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     *
     * @param userLoginVO
     * @return
     */
    public LoginResult loginByPassword(UserLoginVO userLoginVO){
        UserInfor userInfor = userMapper.getInforByPhoneNum(userLoginVO.getPhoneNum());
        if(userInfor==null)
            return new LoginResult("",false,"null");
        String pass= MD5Producer.getMD5String(userLoginVO.getPassword(),userInfor.getSalt());
        if(userInfor.getPassword().equals(pass)){
            if(redisTemplate.opsForValue().get(userInfor.getUuid())!=null)//单点登录,有漏洞
                return new LoginResult("",false,"您的账号已经在另一个地方登录!");
            String token = JWTProducer.getToken(userInfor.getUuid());
            amqpTemplate.convertAndSend(exchange,routeKey,userInfor);
            amqpTemplate.convertAndSend(exchange,timeRouteKey,new LastTime(userInfor.getUuid(), LocalDate.now()));
            return new LoginResult(token,true,"登录成功!");
        }else{
            return new LoginResult("",false,"密码错误!");
        }
    }

//    /**
//     *
//     * @param tokenVO
//     * @return
//     */
//    public LoginResult loginByToken(TokenVO tokenVO){
//        DecodedJWT verify = JWTProducer.verify(tokenVO.getToken());
//        Claim uuid = verify.getClaim("uuid");
//        UserInfor userInfor = (UserInfor)redisTemplate.opsForValue().get(uuid.asString());
//        LoginResult loginResult=null;
//        if(userInfor==null)
//            loginResult=new LoginResult("",false,"令牌过期或不存在");
//        else{
//            loginResult=new LoginResult("",true,"登录成功");
//            amqpTemplate.convertAndSend(exchange,freshRouteKey,uuid.asString());
//        }
//        return loginResult;
//    }

    /**
     *
     * @param tokenVO
     * @return
     */
    public LoginResult logOutByToken(TokenVO tokenVO){
        DecodedJWT verify = JWTProducer.verify(tokenVO.getToken());
        Claim uuid = verify.getClaim("uuid");
        LoginResult loginResult=new LoginResult("",true,"登出成功");
        amqpTemplate.convertAndSend(exchange,logOutRouteKey,uuid.asString());
        return loginResult;
    }
}
