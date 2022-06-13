package com.example.gateway.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfor implements Serializable {
        @Serial
        private static final long serialVersionUID = -1098734102366247173L;
        private String uuid;
        private String nickName;
        private String phoneNum;
        private String password;
        private String slat;
}
