package com.api.v1.chat;

import com.api.auth.service.JwtService;
import com.core.domain.chat.dto.DirectMessageTotalRequest;
import com.core.domain.chat.dto.DirectMessageTotalResponse;
import com.core.domain.chat.repository.DirectMessageRoomRepository;
import com.core.domain.deal.dto.ProtectedDealResponse;
import com.core.domain.deal.model.ProtectedDeal;
import com.core.domain.deal.repository.ProtectedDealRepository;
import com.core.domain.home.model.Home;
import com.core.domain.home.repository.HomeRepository;
import com.core.domain.user.model.User;
import com.core.domain.user.repository.UserRepository;
import com.core.domain.user.service.UserService;
import com.core.mapper.HomeMapper;
import com.core.mapper.ProtectedDealMapper;
import com.core.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infra.utils.SuccessResponse;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.api.v1.constants.ApiUrlConstants.CHAT_TOTAL_URL;
import static com.api.v1.constants.ApiUrlConstants.DEALS_READ;
import static com.api.v1.constants.ResponseMessage.FIND_CHATTING_ROOM;
import static com.api.v1.constants.ResponseMessage.FIND_PROTECTED_DEAL;
import static com.core.chat.dto_helper.DirectMessageDtoHelper.generateDirectMessageTotalRequest;
import static com.core.deal.dto_helper.ProtectedDealDtoHelper.generateProtectedDealFindRequest;
import static com.core.deal.entity_helper.ProtectedDealHelper.generateProtectedDeal;
import static com.core.deal.entity_helper.ProtectedDealHelper.generateProtectedDealWithUserIds;
import static com.core.home.entity_helper.HomeHelper.generateHome;
import static com.core.user.entity_helper.UserHelper.generateUser;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HomeRepository homeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProtectedDealRepository protectedDealRepository;
    @Autowired
    private DirectMessageRoomRepository directMessageRoomRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HomeMapper homeMapper;
    @Autowired
    private ProtectedDealMapper protectedDealMapper;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private SecurityFilterChain securityFilterChain;
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
    void 채팅_방에_필요한_정보를_조회한다() throws Exception {
        //given
        User sender = userRepository.save(generateUser(1L));
        User receiver = userRepository.save(generateUser(2L));
        Home home = homeRepository.save(generateHome(sender.getId()));
        ProtectedDeal protectedDeal = protectedDealRepository.save(generateProtectedDealWithUserIds(home.getId(), receiver.getId(), sender.getId()));
        DirectMessageTotalRequest directMessageTotalRequest = generateDirectMessageTotalRequest(home.getId(), receiver.getId(), 1L, sender.getId());
        DirectMessageTotalResponse response = DirectMessageTotalResponse.builder()
                .sender(userMapper.toProfile(sender))
                .receiver(userMapper.toProfile(receiver))
                .homeInformationResponse(homeMapper.toHomeInformation(home, sender))
                .isExistAccount(false)
                .protectedDealResponse(List.of(protectedDealMapper.toResponse(protectedDeal, home)))
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post(CHAT_TOTAL_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directMessageTotalRequest))
                        .header(HttpHeaders.AUTHORIZATION, token))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, FIND_CHATTING_ROOM, response))));
    }
}
