package com.example.admin.sevices;

import com.example.admin.mappers.SKUMapper;
import com.example.admin.tools.FilePathProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@Service
public class ImgService {
    @Autowired
    private SKUMapper skuMapper;

    /**
     * 上传图片
     * @param sku
     * @param file
     * @return
     */
    public boolean uploadImg(int sku,MultipartFile file){
        try{
            String path = FilePathProducer.getPath(sku, file);
            skuMapper.updateImage(sku,path);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取服务器上的img
     * @param sku
     * @return
     */
    public byte[] getImg(int sku){
        try{
            String path = skuMapper.getImg(sku);
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
