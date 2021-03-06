package com.example.user.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserContext {
    private String uuid;
    private String nickName;
    private String phoneNum;
    private LocalDate localDate;
    private String address;
}
