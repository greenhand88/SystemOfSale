package com.example.admin.sevices;

import com.example.admin.VO.SPUVO;
import com.example.admin.mappers.SPUMapper;
import com.example.admin.tools.UUIDProducer;
import com.example.admin.utils.SPUInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SPUService {
    /*
    低并发,直接用数据库,减少程序复杂度
     */
    @Autowired
    private SPUMapper SPUMapper;

    /**
     * 上架商品
     * @param spuvo
     * @return
     */
    public void addSPU(SPUVO spuvo) throws Exception{
        String uuid = UUIDProducer.getUUID();
        //数据库外键约束改成CASCADE
        SPUMapper.addSPU(uuid, spuvo.getGoodName(),
                spuvo.getGoodDescription(), LocalDateTime.now());
    }
    /**
     * 下架某种商品
     * @param uuid
     * @return
     */
    public void deleteSPU(String uuid)throws Exception{
        //数据库外键约束改成CASCADE
        SPUMapper.deleteGoods(uuid);
    }

    /**
     * 获取所有商品大类
     * @return
     */
    public List<SPUInfor> getSPUs(){
        try{
            return SPUMapper.getGoods();
        }catch (Exception e){
            return new ArrayList<>();
        }
    }


}
