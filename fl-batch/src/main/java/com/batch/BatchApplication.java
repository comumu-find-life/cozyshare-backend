package com.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure..EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"com.service","com.batch", "com.core", "com.infra"})
//@EnableJpaRepositories(basePackages = {"com.core.api_core.chat.repository", "com.core.api_core.user.repository"})
//@EntityScan(basePackages = { "com.core.chat_core.chat.model", "com.core.api_core.chat.model", "com.core.api_core.user.model"})
public class BatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }
}
