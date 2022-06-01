package com.example.user.tools;
import org.apache.commons.codec.digest.DigestUtils;


public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }
    private static final String salt="zzjNb";
    public static String process(String src){
        return md5(src+salt);
    }
}
