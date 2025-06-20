//package com.core.logger;
//
//
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicLong;
//
//public class QueryStatsHolder {
//
//    private static final ConcurrentHashMap<String, AtomicLong> methodCallCount = new ConcurrentHashMap<>();
//
//    public static void increment(String methodName) {
//        methodCallCount
//                .computeIfAbsent(methodName, k -> new AtomicLong(0))
//                .incrementAndGet();
//    }
//
//    public static long getCount(String methodName) {
//        return methodCallCount.getOrDefault(methodName, new AtomicLong(0)).get();
//    }
//
//    public static ConcurrentHashMap<String, AtomicLong> getAllStats() {
//        return methodCallCount;
//    }
//
//    public static void reset() {
//        methodCallCount.clear();
//    }
//}