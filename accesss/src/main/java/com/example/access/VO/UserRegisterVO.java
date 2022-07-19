package com.example.access.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterVO {
    private String phoneNum;
    private String password;
    private String nickName;
}
