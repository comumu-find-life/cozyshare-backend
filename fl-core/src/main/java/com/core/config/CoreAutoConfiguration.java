package com.core.config;


import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@AutoConfiguration
@Configuration
@EnableMongoRepositories(basePackages = {"com.core"})
@EntityScan(basePackages = "com.core")
@EnableJpaRepositories(basePackages = "com.core")
@ComponentScan(basePackages = "com.core")
public class CoreAutoConfiguration {
}