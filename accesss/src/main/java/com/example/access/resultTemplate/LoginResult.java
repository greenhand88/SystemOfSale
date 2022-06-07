package com.example.access.resultTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {
    private String token;
    private Boolean pass;
    private String message;
}
