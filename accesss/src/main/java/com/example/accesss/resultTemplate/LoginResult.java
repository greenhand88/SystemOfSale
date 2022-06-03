package com.example.accesss.resultTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {
    String token;
    Boolean pass;
    String message;
}
