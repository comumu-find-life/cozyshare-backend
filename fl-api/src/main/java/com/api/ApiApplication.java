package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ApiApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Australia/Sydney"));
        SpringApplication.run(ApiApplication.class, args);
    }
}