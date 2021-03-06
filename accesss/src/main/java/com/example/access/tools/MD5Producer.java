package com.example.access.tools;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Producer {
    private static final String salt="audwoqdiuqgas";
    public static String getSalt(){
        return salt;
    }
    public static String getMD5String(String text){
        return DigestUtils.md5Hex(text+salt);
    }
    public static String getMD5String(String text,String newSalt){
        return DigestUtils.md5Hex(text+newSalt);
    }
}
