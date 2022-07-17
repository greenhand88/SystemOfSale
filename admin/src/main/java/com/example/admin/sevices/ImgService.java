package com.example.admin.sevices;

import com.example.admin.mappers.GoodDetailMapper;
import com.example.admin.tools.FilePathProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@Service
public class ImgService {
    @Autowired
    private GoodDetailMapper goodDetailMapper;

    /**
     * 上传图片
     * @param uuid
     * @param file
     * @return
     */
    public boolean uploadImg(String uuid,MultipartFile file){
        try{
            String path = FilePathProducer.getPath(uuid, file);
            goodDetailMapper.updateImage(uuid,path);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取服务器上的img
     * @param uuid
     * @return
     */
    public byte[] getImg(String uuid){
        try{
            String path = goodDetailMapper.getImg(uuid);
            File file = new File(path);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            inputStream.close();
            return bytes;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
