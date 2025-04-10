package com.infra.email.service;

public interface EmailVerificationService {

    void sendVerificationCode(final String email);

    boolean validateVerificationCode(final String email, final String code);
}
