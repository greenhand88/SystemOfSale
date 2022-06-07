package com.example.access.services;


import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.access.VO.TokenVO;
import com.example.access.VO.UserLoginVO;
import com.example.access.mappers.UserMapper;
import com.example.access.resultTemplate.LoginResult;
import com.example.access.tools.JWTProducer;
import com.example.access.tools.MD5Producer;
import com.example.access.utils.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${mq.config.access.exchange}")
    private String exchange;
    @Value("${mq.config.access.routeKey}")
    private String routeKey;
    @Value("${mq.config.fresh.routeKey}")
    private String freshRouteKey;
    @Value("${mq.config.logOut.routeKey}")
    private String logOutRouteKey;

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
