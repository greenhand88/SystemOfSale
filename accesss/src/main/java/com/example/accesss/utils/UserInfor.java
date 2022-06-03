package com.example.accesss.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfor {
        String uuid;
        String nickName;
        String phoneNum;
        String password;
        String slat;
}
