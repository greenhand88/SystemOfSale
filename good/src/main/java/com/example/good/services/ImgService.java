package com.example.good.services;

import com.example.good.mappers.GoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@Service
public class ImgService {
    @Autowired
    private GoodMapper goodMapper;

    /**
     * 获取服务器上的img
     * @param uuid
     * @return
     */
    public byte[] getImg(String uuid){
        try{
            String path = goodMapper.getImg(uuid);
            File file = new File(path);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            return bytes;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
