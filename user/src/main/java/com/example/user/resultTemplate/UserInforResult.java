package com.example.user.resultTemplate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInforResult {
    private String uuid;
    private String nickName;
    private String phoneNum;
    private LocalDate localDate;
    private String address;
    private String message;
}
