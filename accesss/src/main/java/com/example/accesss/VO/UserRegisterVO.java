package com.example.accesss.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterVO {
    String phoneNum;
    String passsword;
    String nickName;
}
