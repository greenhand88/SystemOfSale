package com.example.access.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterInfor implements Serializable {
    private String uuid;
    private String phoneNum;
    private String md5;
    private String nickName;
    private String salt;
}
