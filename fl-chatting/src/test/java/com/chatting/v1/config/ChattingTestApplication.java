package com.chatting.v1.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.chatting", "com.core", "com.infra"}) // 꼭 필요한 모듈만 지정
public class ChattingTestApplication {}