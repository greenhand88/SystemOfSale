package com.example.access.services;

import com.example.access.VO.ChangeVO;
import com.example.access.mappers.UserMapper;
import com.example.access.resultTemplate.LoginResult;
import com.example.access.tools.MD5Producer;
import com.example.access.utils.UserInfor;
import com.example.access.utils.UserPInfor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class UserChangePService {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Value("${mq.config.changePass.exchange}")
    private String exchange;
    @Value("${mq.config.changePass.routeKey}")
    private String routeKey;
    @Autowired
    private UserMapper userMapper;
    public LoginResult changePassword(ChangeVO changeVO){
        UserInfor inforByPhoneNum = userMapper.getInforByPhoneNum(changeVO.getPhoneNum());
        String password= MD5Producer.getMD5String(changeVO.getOldPassword(),inforByPhoneNum.getSlat());
        if(password.equals(inforByPhoneNum.getPassword())){
            String md5String = MD5Producer.getMD5String(changeVO.getNewPassword(), inforByPhoneNum.getSlat());
            sendNewPassword(new UserPInfor(inforByPhoneNum.getUuid(),md5String));
            return new LoginResult("",false,"密码修改成功!");
        }else{
            return new LoginResult("",false,"账号与密码不匹配!");
        }

    }
    private void sendNewPassword(UserPInfor newPassword){
        amqpTemplate.convertAndSend(exchange,routeKey,newPassword);
    }
}
