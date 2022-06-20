package com.example.admin.tools;

import java.util.UUID;

public class UUIDProducer {
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
}
