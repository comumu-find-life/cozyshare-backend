package com.api.v1.user;

import com.core.user.dto.UserAccountRequest;
import com.core.user.dto.UserAccountResponse;
import com.infra.utils.SuccessResponse;
import com.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.api.v1.constants.ApiUrlConstants.*;
import static com.api.v1.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AccountController {

    private final UserService userService;

    /**
     * 계좌 등록 API
     */
    @PostMapping(USER_ACCOUNT_REGISTER_URL)
    public ResponseEntity<?> registerAccount(@RequestBody final UserAccountRequest userAccountRequest, @PathVariable final Long userId) {
        userService.registerUserAccount(userAccountRequest, userId);
        SuccessResponse response = new SuccessResponse(true, MY_ACCOUNT_REGISTER, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 사용자 계좌 정보 조회 API
     */
    @GetMapping(USER_ACCOUNT_REGISTER_URL)
    public ResponseEntity<?> findUserAccount(@PathVariable final Long userId) {
        UserAccountResponse userAccountById = userService.findUserAccountById(userId);
        SuccessResponse response = new SuccessResponse(true, MY_ACCOUNT_FIND, userAccountById);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 사용자 계좌 정보 업데이트 API
     */
    @PatchMapping(USER_ACCOUNT_REGISTER_URL)
    public ResponseEntity<?> updateAccount(@RequestBody final UserAccountRequest userAccountRequest, @PathVariable final Long userId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateAccount(userAccountRequest, email);
        SuccessResponse response = new SuccessResponse(true, MY_ACCOUNT_UPDATE, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 사용자 계좌 유/무 검증 API
     */
    @GetMapping(USER_ACCOUNT_EXIST_URL)
    public ResponseEntity<?> validateAccountExist(@PathVariable final Long userId) {
        boolean result = userService.isExistAccount(userId);
        SuccessResponse response = new SuccessResponse(true, MY_ACCOUNT_EXIST, result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
