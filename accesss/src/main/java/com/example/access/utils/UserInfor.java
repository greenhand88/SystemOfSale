package com.example.access.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfor implements Serializable {
        private String uuid;
        private String nickName;
        private String phoneNum;
        private String password;
        private String slat;
}
