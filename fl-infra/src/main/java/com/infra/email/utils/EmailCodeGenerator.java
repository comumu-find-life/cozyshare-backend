package com.infra.email.utils;

import java.util.Random;

public class EmailCodeGenerator {
    private static final int CODE_LENGTH = 6;

    public static String generateVerificationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
