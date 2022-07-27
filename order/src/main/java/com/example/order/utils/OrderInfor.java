package com.example.order.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfor implements Serializable {
    @Serial
    private static final long serialVersionUID = -164678967570199907L;
    private Map<String,Integer> map;
    private String uuid;
    private String orderUuid;
}
