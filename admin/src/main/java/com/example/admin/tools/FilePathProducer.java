package com.example.admin.tools;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FilePathProducer {
    public static String getPath(int sku,MultipartFile file){
        if (file.isEmpty()) {
            return "文件为空!";
        }
        // 给文件重命名
        String fileName = String.valueOf(sku)+ "." + file.getContentType()
                .substring(file.getContentType().lastIndexOf("/") + 1);
        String path = getPath();
        try {
            // 获取保存路径
            File files = new File(path, fileName);
            File parentFile = files.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdir();
            }
            file.transferTo(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path+fileName; // 返回重命名后的文件名
    }
    private static String getPath(){
        // 这里需要注意的是ApplicationHome是属于SpringBoot的类
        // 获取项目下resources/static/img路径
        ApplicationHome applicationHome = new ApplicationHome(FilePathProducer.class);
        // 保存目录位置根据项目需求可随意更改
        return applicationHome.getDir().getParentFile()
                .getParentFile().getAbsolutePath() +"\\"+"imgs"+"\\";
    }
}
