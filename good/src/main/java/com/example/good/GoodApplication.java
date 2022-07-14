package com.example.good;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.good.mappers")
public class GoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodApplication.class, args);
    }

}
