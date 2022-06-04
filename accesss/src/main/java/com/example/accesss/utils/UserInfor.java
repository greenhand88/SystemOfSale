package com.example.accesss.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfor implements Serializable {
        String uuid;
        String nickName;
        String phoneNum;
        String password;
        String slat;
}
