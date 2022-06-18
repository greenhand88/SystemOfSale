package com.example.user.services;

import com.example.user.mappers.UserInforMapper;
import com.example.user.utils.UserAddress;
import com.example.user.utils.UserContext;
import com.example.user.utils.UserInfor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQService {
    @Autowired
    UserInforMapper userInforMapper;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.userInfor.queue}"),
            exchange = @Exchange("${mq.config.userInfor.exchange}"),
            key="${mq.config.userInfor.routeKey}"))
    public void addInfor(UserInfor userInfor){
        UserContext allInfor = userInforMapper.getAllInfor(userInfor.getUuid());
        if(allInfor==null){
            userInforMapper.basicAdd(userInfor.getUuid(),userInfor.getPhoneNum(),userInfor.getNickName());
        }
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.address.queue}"),
            exchange = @Exchange("${mq.config.address.exchange}"),
            key="${mq.config.address.routeKey}"))
    public void addAddress(UserAddress userAddress){
        userInforMapper.addAddress(userAddress.getAddress(),userAddress.getUuid());
    }
}
