package com.api.v1.deal;

import com.api.auth.service.JwtService;
import com.core.domain.deal.dto.ProtectedDealResponse;
import com.core.domain.deal.model.ProtectedDeal;
import com.core.domain.deal.repository.ProtectedDealRepository;
import com.core.domain.home.model.Home;
import com.core.domain.home.repository.HomeRepository;
import com.core.domain.user.model.User;
import com.core.domain.user.repository.UserAccountRepository;
import com.core.domain.user.repository.UserRepository;
import com.core.domain.user.service.UserService;
import com.core.mapper.ProtectedDealMapper;
import com.core.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infra.utils.SuccessResponse;
import org.assertj.core.api.Assertions;
import org.springframework.security.core.context.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.api.v1.constants.ApiUrlConstants.*;
import static com.api.v1.constants.ResponseMessage.*;
import static com.core.deal.dto_helper.ProtectedDealDtoHelper.generateProtectedDealFindRequest;
import static com.core.deal.dto_helper.ProtectedDealDtoHelper.generateProtectedDealGeneratorRequest;
import static com.core.deal.entity_helper.ProtectedDealHelper.*;
import static com.core.home.entity_helper.HomeHelper.generateHome;
import static com.core.user.entity_helper.UserHelper.generateUser;
import static com.core.user.entity_helper.UserHelper.generateUserAccount;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ProtectedDealControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProtectedDealRepository protectedDealRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private ProtectedDealMapper protectedDealMapper;

    @Autowired
    private UserMapper userMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserService userService;

    @MockBean
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Mock
    private SecurityContext securityContext;

    private String token;

    @BeforeEach
    public void setUp() {
        token = "Bearer your-jwt-token";
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(authentication.getName()).thenReturn("test@example.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void 안전거래를_조회한다() throws Exception {
        //given
        Home home = homeRepository.save(generateHome(1L));
        ProtectedDeal protectedDeal = protectedDealRepository.save(generateProtectedDeal(home.getId()));
        ProtectedDealResponse protectedDealResponse = protectedDealMapper.toResponse(protectedDeal, home);
        List<ProtectedDealResponse> responses = List.of(protectedDealResponse);
        // when
        mockMvc.perform(MockMvcRequestBuilders.post(DEALS_READ)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generateProtectedDealFindRequest(home.getId())))
                        .header(HttpHeaders.AUTHORIZATION, token))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, FIND_PROTECTED_DEAL, responses))));
    }

    @Test
    public void 자신의_거래_내역을_모두_조회한다() throws Exception {
        //given
        Home home = homeRepository.save(generateHome(1L));
        ProtectedDeal protectedDeal = protectedDealRepository.save(generateProtectedDeal(home.getId()));
        ProtectedDealResponse protectedDealResponse = protectedDealMapper.toResponse(protectedDeal, home);
        List<ProtectedDealResponse> responses = List.of(protectedDealResponse);
        // when
        mockMvc.perform(MockMvcRequestBuilders.get(DEALS_FIND_ALL_BY_USER_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generateProtectedDealFindRequest(home.getId())))
                        .header(HttpHeaders.AUTHORIZATION, token))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, FIND_MY_PROTECTED_DEAL, responses))));
    }

    @Test
    public void 안전거래를_생성한다() throws Exception {
        //given
        Home home = homeRepository.save(generateHome(1L));
        // when
        mockMvc.perform(MockMvcRequestBuilders.post(DEALS_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generateProtectedDealGeneratorRequest(home.getId())))
                        .header(HttpHeaders.AUTHORIZATION, token))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void 안전거래를_임차인이_수락한다() throws Exception {
        //given
        Home home = homeRepository.save(generateHome(1L));
        User getter = userRepository.save(generateUser(1L));
        ProtectedDeal deal = protectedDealRepository.save(generateProtectedDealWithGetterId(home.getId(), getter.getId()));
        when(userService.findByEmail(anyString())).thenReturn(userMapper.toDto(getter));
        userAccountRepository.save(generateUserAccount(getter.getId(), 10000));

        // when
        mockMvc.perform(MockMvcRequestBuilders.post(DEALS_ACCEPT_REQUEST, deal.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, ACCEPT_DEAL, null))));
    }

    @Test
    public void 안전거래를_임차인이_완료한다() throws Exception {
        //given
        Home home = homeRepository.save(generateHome(1L));
        User getter = userRepository.save(generateUser(1L));
        User provider = userRepository.save(generateUser(2L));
        userAccountRepository.save(generateUserAccount(provider.getId(), 100));

        ProtectedDeal deal = protectedDealRepository.save(generateProtectedDealWithUserIds(home.getId(), getter.getId(), provider.getId()));
        when(userService.findByEmail(anyString())).thenReturn(userMapper.toDto(getter));
        userAccountRepository.save(generateUserAccount(getter.getId(), 10000));

        // when
        mockMvc.perform(MockMvcRequestBuilders.patch(DEALS_REQUEST_COMPLETE_URL, deal.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, COMPLETE_DEAL, null))));

        //거래 완료 후 임대인 포인트 증가
        Assertions.assertThat(userAccountRepository.findByUserId(provider.getId()).get().getPoint()).isEqualTo(2100.0);
    }

    @Test
    void 보증금을_입금하기전에_안전거래를_임차인이_취소한다() throws Exception {
        //given
        Home home = homeRepository.save(generateHome(1L));
        User getter = userRepository.save(generateUser(1L));
        ProtectedDeal deal = protectedDealRepository.save(generateProtectedDealWithGetterId(home.getId(), getter.getId()));
        when(userService.findByEmail(anyString())).thenReturn(userMapper.toDto(getter));
        userAccountRepository.save(generateUserAccount(getter.getId(), 10000));

        // when
        mockMvc.perform(MockMvcRequestBuilders.patch(DEALS_CANCEL_BEFORE_URL, deal.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, CANCEL_BEFORE_WITHDRAW_DEAL, null))));
    }

    @Test
    void 보증금을_입금한_후_안전거래를_임차인이_취소한다() throws Exception {
        //given
        Home home = homeRepository.save(generateHome(1L));
        User getter = userRepository.save(generateUser(1L));
        ProtectedDeal deal = protectedDealRepository.save(generateProtectedDealWithGetterId(home.getId(), getter.getId()));
        when(userService.findByEmail(anyString())).thenReturn(userMapper.toDto(getter));
        userAccountRepository.save(generateUserAccount(getter.getId(), 10000));

        // when
        mockMvc.perform(MockMvcRequestBuilders.patch(DEALS_CANCEL_AFTER, deal.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, CANCEL_AFTER_WITHDRAW_DEAL, null))));
    }
}
