package com.core.config;

import com.core.utils.TimerAop;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;

@TestConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TestConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Primary
    @Bean(name = "testJpaQueryFactory")
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public TimerAop timerAop() {
        return new TimerAop();
    }
}
