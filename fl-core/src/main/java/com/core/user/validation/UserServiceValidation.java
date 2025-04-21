package com.core.user.validation;

public interface UserServiceValidation {
    void validateSignUp(String email, String nickName) throws Exception;
}
