package com.core.domain.user.service;

import com.core.domain.user.validation.UserServiceValidation;
import com.infra.file.FileHelper;
import com.core.mapper.UserMapper;
import com.core.domain.user.dto.UserAccountRequest;
import com.core.domain.user.dto.UserProfileUpdateRequest;
import com.core.domain.user.dto.UserSignupRequest;
import com.core.domain.user.dto.UserAccountResponse;
import com.core.domain.user.dto.UserInformationResponse;
import com.core.domain.user.dto.UserProfileResponse;
import com.core.domain.user.dto.WithDrawHistoryResponse;
import com.core.domain.chat.repository.DirectMessageRoomRepository;
import com.core.domain.user.model.ChargeType;
import com.core.domain.user.model.PointHistory;
import com.core.domain.user.model.User;
import com.core.domain.user.model.UserAccount;
import com.core.domain.user.repository.UserAccountRepository;
import com.core.domain.user.repository.UserRepository;
import com.infra.utils.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static com.infra.exception.ExceptionMessages.NOT_EXIST_USER_EMAIL;
import static com.infra.exception.ExceptionMessages.NOT_EXIST_USER_ID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final FileHelper fileService;
    private final UserRepository userRepository;
    private final DirectMessageRoomRepository directMessageRoomRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserServiceValidation validation;


    public Long signUp(final UserSignupRequest userSignupRequest, final String encodePassword, final MultipartFile image) throws Exception {
        validation.validateSignUp(userSignupRequest.getEmail(), userSignupRequest.getNickname());
        User user = createUser(userSignupRequest, encodePassword, image);
        return userRepository.save(user).getId();
    }

    public List<UserProfileResponse> findSenderReceiver(final Long senderId, final Long receiverId) {
        List<User> users = userRepository.findSenderAndReceiver(senderId, receiverId);
        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, user -> user));
        User senderUser = userMap.get(senderId);
        User receiverUser = userMap.get(receiverId);
        return Arrays.asList(userMapper.toProfile(senderUser), userMapper.toProfile(receiverUser));
    }

    public UserProfileResponse getUserProfile(final Long id) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(id), NOT_EXIST_USER_ID);
        return userMapper.toProfile(user);
    }

    public boolean isExistEmail(final String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.isEmpty();
    }

    public UserInformationResponse findById(final Long id) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(id), NOT_EXIST_USER_ID);
        return userMapper.toDto(user);
    }

    public UserInformationResponse findByEmail(final String email) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIST_USER_EMAIL);
        return userMapper.toDto(user);
    }

    @Transactional
    public void update(final UserProfileUpdateRequest userProfileUpdateRequest) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(userProfileUpdateRequest.getUserId()), NOT_EXIST_USER_ID);
        userMapper.updateUser(userProfileUpdateRequest, user);
        userRepository.save(user);
    }

    @Transactional
    public void updateAccount(final UserAccountRequest userAccountRequest, String email) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIST_USER_EMAIL);
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(user.getId()), NOT_EXIST_USER_ID);
        userMapper.updateUserAccount(userAccountRequest, userAccount);
    }

    @Transactional
    public void updateImage(final Long userId, final MultipartFile image) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(userId), NOT_EXIST_USER_ID);
        if (user.isExistProfile()) {
            fileService.deleteFile(user.getProfileUrl());
        }
        user.setProfileUrl(uploadProfileImage(image));
    }

    @Transactional
    @CacheEvict(value = "homeOverviewCache", key = "'allHomes'", allEntries = true)
    public void delete(final String email) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIST_USER_ID);
        Optional<UserAccount> userAccount = userAccountRepository.findByUserId(user.getId());
        if (userAccount.isPresent()) {
            userAccountRepository.delete(userAccount.get());
        }
        directMessageRoomRepository.deleteAllByUserId(user.getId());
        userRepository.delete(user);
    }

    @Transactional
    public void updateRefreshToken(final String email, final String refreshToken) {
        userRepository.findByEmail(email).get().setRefreshToken(refreshToken);
    }

    public void registerUserAccount(final UserAccountRequest userAccountRequest, final Long userId) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(userId), NOT_EXIST_USER_ID);
        UserAccount userAccount = userMapper.toUserAccount(userAccountRequest, user.getId());
        userAccountRepository.save(userAccount);
    }

    public boolean isExistAccount(final Long userId) {
        Optional<UserAccount> userAccount = userAccountRepository.findByUserId(userId);
        return !userAccount.isEmpty();
    }

    public UserAccountResponse findUserAccountById(final Long userId) {
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(userId), NOT_EXIST_USER_ID);
        return userMapper.toUserAccountResponse(userAccount);
    }

    public boolean isExistAccountByEmail(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private User createUser(UserSignupRequest dto, String password, MultipartFile image) {
        User user = userMapper.toEntity(dto);
        user.setPassword(password);
        if (image != null) {
            String profileUrl = uploadProfileImage(image);
            user.setProfileUrl(profileUrl);
        }
        return user;
    }

    private String uploadProfileImage(final MultipartFile image) {
        String url = fileService.toUrls(image);
        fileService.fileUpload(image, url);
        return url;
    }

    @Transactional
    public void updateFcmToken(final String email, final String fcmToken) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIST_USER_EMAIL);
        user.setFcmToken(fcmToken);
    }
}
