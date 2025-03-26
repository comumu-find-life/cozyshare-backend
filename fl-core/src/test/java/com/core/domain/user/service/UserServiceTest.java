package com.core.domain.user.service;

import com.core.domain.chat.repository.DirectMessageRoomRepository;
import com.core.domain.user.dto.UserProfileResponse;
import com.core.domain.user.dto.UserSignupRequest;
import com.core.domain.user.model.User;
import com.core.domain.user.repository.UserAccountRepository;
import com.core.domain.user.repository.UserRepository;
import com.core.domain.user.validation.UserServiceValidation;
import com.core.mapper.UserMapper;
import com.infra.file.FileHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.core.user.dto_helper.UserDtoHelper.generateUserSignupRequest;
import static com.core.user.entity_helper.UserHelper.generateUser;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private FileHelper fileHelper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private DirectMessageRoomRepository directMessageRoomRepository;

    @Mock
    private UserServiceValidation validation;

    @Test
    void 사용자_정보를_기반으로_회원가입을_한다() throws Exception {
        String encodedPassword = "encodedPassword";
        MultipartFile image = mock(MultipartFile.class);
        User user = generateUser(1L);
        UserSignupRequest userSignupRequest = generateUserSignupRequest();
        when(userMapper.toEntity(userSignupRequest)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        Long userId = userService.signUp(userSignupRequest, encodedPassword, image);

        // Then
        assertEquals(user.getId(), userId);
    }

    @Test
    void 회원가입_중복_이메일_검사() {
        // Given
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(generateUser(1L)));

        // When
        boolean isUnique = userService.isExistEmail("test@example.com");

        // Then
        assertFalse(isUnique);
    }

    @Test
    void 임대인_임차인의_정보를_순차적으로_조회한다() {

        User sender = generateUser(1L);
        User receiver = generateUser(2L);
        when(userRepository.findSenderAndReceiver(1L, 2L)).thenReturn(List.of(sender, receiver));

        List<UserProfileResponse> senderReceiver = userService.findSenderReceiver(sender.getId(), receiver.getId());

        Assertions.assertThat(senderReceiver.size()).isEqualTo(2);
    }
}
