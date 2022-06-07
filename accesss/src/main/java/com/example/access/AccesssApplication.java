package com.example.access;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.example.access.mappers")
public class AccesssApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccesssApplication.class, args);
    }

}
