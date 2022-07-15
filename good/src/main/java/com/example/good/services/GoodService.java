package com.example.good.services;

import com.example.good.mappers.GoodMapper;
import com.example.good.utils.GoodInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GoodService {
    @Autowired
    private GoodMapper goodMapper;

    /**
     * 默认分页大小为10
     * @return
     */
    public List<GoodInfor>getGoods(int page){
        page=(page-1)*10;
        return goodMapper.getGoods(page);
    }

    /**
     * 自定义分页大小
     * @param n
     * @return
     */
    public List<GoodInfor>getGoods(int page,int n){
        if(n<=0)
            return new ArrayList<>();
        page=(page-1)*n;
        return goodMapper.getGoodsLimitN(page,n);
    }

    /**
     * 搜索商品
     * @param name
     * @return
     */
    public List<GoodInfor>searchGoods(String name){
        return goodMapper.searchForGood("%"+name+"%");
    }

}
