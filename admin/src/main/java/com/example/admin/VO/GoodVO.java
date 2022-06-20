package com.example.admin.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoodVO {
    private String goodName;
    private String goodDescription;
    private double price;
    private int stock;
}
