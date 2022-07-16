package com.example.admin.sevices;

import com.example.admin.mappers.LoginMapper;
import com.example.admin.tools.JWTProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;

    public String veritify(String account,String password){
        try{
            if (password.equals(loginMapper.getPassword(account)))
                return JWTProducer.getToken(account);
            else
                return new String("密码错误");
        }catch (Exception e){
            e.printStackTrace();
            return new String("出现异常");
        }
    }
}
