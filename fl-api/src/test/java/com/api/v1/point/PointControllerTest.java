package com.api.v1.point;

import com.api.auth.service.JwtService;
import com.core.domain.deal.service.PaypalService;
import com.core.domain.user.model.User;
import com.core.domain.user.model.UserAccount;
import com.core.domain.user.repository.UserAccountRepository;
import com.core.domain.user.repository.UserRepository;
import com.core.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infra.utils.SuccessResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.api.v1.constants.ApiUrlConstants.*;
import static com.api.v1.point.SuccessPointMessages.APPLY_WITH_DRAW;
import static com.api.v1.point.SuccessPointMessages.CHARGE_SUCCESS;
import static com.core.point.dto_helper.PointHelper.generatePaymentRequest;
import static com.core.user.entity_helper.UserHelper.generateUser;
import static com.core.user.entity_helper.UserHelper.generateUserAccount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PointControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;
    @Autowired private UserAccountRepository userAccountRepository;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private JwtService jwtService;
    @MockBean private PaypalService paypalService;
    @MockBean private UserService userService;
    @MockBean private SecurityFilterChain securityFilterChain;

    private String token;

    @BeforeEach
    void setUp() {
        token = "Bearer your-jwt-token";
    }

    private User createUserWithAccount(long userId, int initialPoint) {
        User user = userRepository.save(generateUser(userId));
        userAccountRepository.save(generateUserAccount(user.getId(), initialPoint));
        return user;
    }

    private void setUpSecurityContext(User user) {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getEmail());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void 입금_완료_후_포인트를_충전한다() throws Exception {
        // Given
        User user = createUserWithAccount(1L, 0);
        setUpSecurityContext(user);
        when(paypalService.verifyPayment(any())).thenReturn(true);

        // When
        mockMvc.perform(post(CHARGE_POINT_BY_PAYPAL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generatePaymentRequest()))
                        .header(HttpHeaders.AUTHORIZATION, token))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, CHARGE_SUCCESS, null))));

        // Then
        UserAccount updatedAccount = userAccountRepository.findByUserId(user.getId()).orElseThrow();
        assertThat(updatedAccount.getPoint()).isEqualTo(1000);
        assertThat(updatedAccount.getChargeHistories()).hasSize(1);
    }

    @Test
    void 환전_신청을_한다() throws Exception {
        // Given
        User user = createUserWithAccount(1L, 1000);
        setUpSecurityContext(user);
        when(paypalService.verifyPayment(any())).thenReturn(true);

        // When
        mockMvc.perform(post(APPLY_WITH_DRAW_URL)
                        .param("price", "1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generatePaymentRequest()))
                        .header(HttpHeaders.AUTHORIZATION, token))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, APPLY_WITH_DRAW, null))));

        // Then
        UserAccount updatedAccount = userAccountRepository.findByUserId(user.getId()).orElseThrow();
        assertThat(updatedAccount.getPoint()).isEqualTo(0);
        assertThat(updatedAccount.getChargeHistories()).hasSize(1);
    }
}
