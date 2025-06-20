//package com.core.aspect;
//
//import com.core.logger.SlowQueryHolder;
//import com.core.logger.SlowQueryLogger;
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//
//import org.springframework.aop.Advisor;
//import org.springframework.aop.aspectj.AspectJExpressionPointcut;
//import org.springframework.aop.support.DefaultPointcutAdvisor;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SlowQueryAspectConfig {
//
//    private final SlowQueryLogger logger;
//    private final SlowQueryProperties properties;
//
//    public SlowQueryAspectConfig(SlowQueryProperties properties, SlowQueryLogger logger) {
//        this.properties = properties;
//        this.logger = logger;
//    }
//
//    @Bean
//    public Advisor slowQueryAdvisor() {
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression(properties.getRepositoryPointcut());
//
//        MethodInterceptor interceptor = new MethodInterceptor() {
//            @Override
//            public Object invoke(MethodInvocation invocation) throws Throwable {
//                long start = System.currentTimeMillis();
//                Object result = invocation.proceed();
//                long duration = System.currentTimeMillis() - start;
//                if (duration >= properties.getThresholdMillis()) {
//                    String method = invocation.getMethod().toString();
//                    String sql = SlowQueryHolder.getCurrentQuery();
//                    logger.logSlowQuery(method, sql, duration);
//                }
//                return result;
//            }
//        };
//
//        return new DefaultPointcutAdvisor(pointcut, interceptor);
//    }
//}
