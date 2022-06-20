package com.example.access.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LastTime implements Serializable {
    private String uuid;
    private LocalDate localDate;
}
