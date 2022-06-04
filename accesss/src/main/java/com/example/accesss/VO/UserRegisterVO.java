package com.example.accesss.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterVO {
    private String phoneNum;
    private String passsword;
    private String nickName;
}
