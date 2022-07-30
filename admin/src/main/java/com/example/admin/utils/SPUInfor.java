package com.example.admin.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SPUInfor {
    private String spu;
    private String goodName;
    private String subtitle;
    private LocalDateTime date;
}
