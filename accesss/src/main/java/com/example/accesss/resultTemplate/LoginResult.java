package com.example.accesss.resultTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {
    String name;
    String account;
    String token;
    Boolean pass;
}
