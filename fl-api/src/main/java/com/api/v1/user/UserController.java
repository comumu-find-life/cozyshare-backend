package com.api.v1.user;

import com.api.auth.service.JwtService;
import com.infra.email.service.EmailVerificationService;
import com.core.user.dto.UserProfileUpdateRequest;
import com.core.user.dto.UserSignupRequest;
import com.core.user.dto.UserInformationResponse;
import com.core.user.dto.UserProfileResponse;
import com.core.user.service.UserService;
import com.infra.utils.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.api.v1.constants.ApiUrlConstants.*;
import static com.api.v1.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailHelper;

    @PostMapping(SEND_EMAIL_URL)
    public ResponseEntity<?> sendCheckCode(@PathVariable final String email) {
        emailHelper.sendVerificationCode(email);
        SuccessResponse response = new SuccessResponse(true, VERIFICATION_CODE_SENT_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(VERIFICATION_EMAIL_CODE_URL)
    public ResponseEntity<?> verifyCode(@PathVariable final String email, @PathVariable final String code) {
        boolean result = emailHelper.validateVerificationCode(email, code);
        SuccessResponse response = new SuccessResponse(true, EMAIL_VERIFICATION_SUCCESS, result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(USERS_SIGN_UP_EMAIL)
    public ResponseEntity<?> signUp(@RequestPart final  UserSignupRequest userSignupRequest,
                                    @RequestPart(value = "image", required = false) final MultipartFile image) throws Exception {
        Long userId = userService.signUp(userSignupRequest,passwordEncoder.encode(userSignupRequest.getPassword()), image);
        SuccessResponse response = new SuccessResponse(true, SIGN_UP_SUCCESS, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(USERS_SIGN_UP_GOOGLE)
    public ResponseEntity<?> signUpGoogle(@RequestPart final UserSignupRequest userSignupRequest,
                                    @RequestPart(value = "image", required = false) final MultipartFile image) throws Exception {
        Long userId = userService.signUp(userSignupRequest, "",image);
        SuccessResponse response = new SuccessResponse(true, SIGN_UP_SUCCESS, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(USERS_CHECK_DUPLICATE_EMAIL)
    public ResponseEntity<?> validateDuplicateEmail(@PathVariable final String email){
        boolean result = userService.isExistEmail(email);
        SuccessResponse response = new SuccessResponse(true, EMAIL_DUPLICATE_VERIFICATION, result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(USERS_FCM_TOKEN_REGISTER)
    public ResponseEntity<?> registerFcmToken(@RequestParam final String fcmToken){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateFcmToken(email, fcmToken);
        SuccessResponse response = new SuccessResponse(true, FCM_TOKEN_UPDATE, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(USERS_FIND_BY_ID)
    public ResponseEntity<?> findById(@PathVariable final Long userId) {
        UserInformationResponse byId = userService.findById(userId);
        SuccessResponse response = new SuccessResponse(true, MY_PROFILE_RETRIEVE, byId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(USERS_UPDATE)
    public ResponseEntity<?> updateUser(@RequestBody final UserProfileUpdateRequest userProfileUpdateRequest){
        userService.update(userProfileUpdateRequest);
        SuccessResponse response = new SuccessResponse(true, MY_PROFILE_UPDATE, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(USERS_IMAGE_UPDATE)
    public ResponseEntity<?> updateUserImage(@PathVariable final Long userId, @RequestPart(value = "image", required = false) final MultipartFile image) {
        userService.updateImage(userId, image);
        SuccessResponse response = new SuccessResponse(true, MY_PROFILE_UPDATE, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(USERS_GET_MY_USER_ID)
    public ResponseEntity<?> getMyUserId(final HttpServletRequest request) {
        String accessToken  = jwtService.extractAccessToken(request).get();
        String email = jwtService.extractEmail(accessToken).get();
        Long userId = userService.findByEmail(email).getId();
        SuccessResponse response = new SuccessResponse(true, MY_USER_ID_RETRIEVE, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(USERS_GET_PROFILE)
    public ResponseEntity<?> getUserProfile(@PathVariable final Long userId) {
        UserProfileResponse userProfile = userService.getUserProfile(userId);
        SuccessResponse response = new SuccessResponse(true, PROFILE_RETRIEVE, userProfile);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(USERS_BASE_URL)
    public ResponseEntity<?> deleteAccount(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.delete(email);
        SuccessResponse response = new SuccessResponse(true, DELETE_ACCOUNT, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(USERS_RE_LOGIN)
    public ResponseEntity<?> reLogin(){
        SuccessResponse response = new SuccessResponse(true, SIGN_UP_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
