package com.example.order.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface OrderMapper {
    @Insert("insert into order(uuid,totalprice,pay,c_uuid) values(#{uuid},#{totalPrice},#{pay},#{cUuid})")
    public void makeOrder(String uuid,double toatlPrice,boolean pay,String cUuid);

}
