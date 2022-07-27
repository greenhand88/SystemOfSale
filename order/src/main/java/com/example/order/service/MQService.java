package com.example.order.service;

import com.example.order.mappers.GoodMapper;
import com.example.order.mappers.OrderMapper;
import com.example.order.utils.OrderInfor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MQService {
    //todo: 将订单信息刷到数据库里面
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GoodMapper goodMapper;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.order.queue}"),
            exchange = @Exchange("${mq.config.order.exchange}"),
            key="${mq.config.order.routeKey}"))
    public void makeOrder(OrderInfor orderInfor){
        try{
            double sum=0;
            for(String s:orderInfor.getMap().keySet()){
                sum+=goodMapper.getPrice(s);
            }
            //todo:未来优化数据库分支付成功表，未支付表和取消支付表,先刷到redis里面，再走支付系统刷到数据库里面
            orderMapper.makeOrder(orderInfor.getOrderUuid(),sum,false,orderInfor.getUuid());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
