package com.example.access.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeVO {
    private String phoneNum;
    private String oldPassword;
    private String newPassword;
}
