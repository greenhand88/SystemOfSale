package com.example.access.tools;

import java.util.UUID;

public class UUIDProducer {
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
}
