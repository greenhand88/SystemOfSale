package com.example.admin.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SKUInfor {
    private int sku;
    private String spu;
    private String detail;
    private String img;
    private double price;
    private int stock;
}
