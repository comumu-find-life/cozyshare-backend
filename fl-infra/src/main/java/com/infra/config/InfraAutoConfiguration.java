package com.infra.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@AutoConfiguration
@Configuration
@EnableRedisRepositories(basePackages = "com.infra.email")
@ComponentScan(basePackages = "com.infra")
public class InfraAutoConfiguration {
}
