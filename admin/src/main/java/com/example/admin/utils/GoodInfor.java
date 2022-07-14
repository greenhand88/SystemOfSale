package com.example.admin.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoodInfor {
    private String uuid;
    private String goodName;
    private String goodDescription;
    private double price;
    private int stock;
    private LocalDate localDate;
}