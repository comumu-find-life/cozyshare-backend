//package com.core.logger;
//
//public class SlowQueryLog {
//    private final String methodName; // 감지한 메서드
//    private final String sql; // 실행된 쿼리
//    private final long durationMs; // 쿼리 실행 시간
//
//
//    public SlowQueryLog(String methodName, String sql, long durationMs) {
//        this.methodName = methodName;
//        this.sql = sql;
//        this.durationMs = durationMs;
//
//    }
//
//    @Override
//    public String toString() {
//        return String.format(
//                "[%s] %dms\nSQL: %s\n",
//                methodName, durationMs, sql
//        );
//    }
//}
