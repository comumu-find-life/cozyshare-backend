package com.infra.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;


@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Value("${thread.pool.core-pool-size}")
    private int CORE_POOL_SIZE;
    @Value("${thread.pool.max-pool-size}")
    private int MAX_POOL_SIZE;
    @Value("${thread.pool.queue-capacity}")
    private int QUEUE_CAPACITY;

    private static final Logger logger = LogManager.getLogger(AsyncConfig.class);

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandlerImpl();
    }

    static class AsyncUncaughtExceptionHandlerImpl implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable ex, Method method, Object... params) {
            logger.error(ex.getMessage());
            logger.error("Method name - {}", method.getName());
            for (Object param : params) {
                logger.error("Parameter value - {}", param);
            }
        }
    }
}