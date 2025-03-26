package com.api.v1.home;

import com.api.auth.service.JwtService;
import com.core.domain.home.dto.HomeGeneratorRequest;
import com.core.domain.home.repository.HomeRepository;
import com.core.domain.home.service.LocationServiceImpl;
import com.core.domain.user.service.UserService;
import com.core.utils.TimerAop;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infra.utils.SuccessResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static com.api.v1.constants.ApiUrlConstants.HOMES_BASE_URL;
import static com.core.home.dto_helper.HomeDtoHelper.generateHomeGeneratorRequest;
import static com.core.home.entity_helper.HomeHelper.generateHome;
import static com.core.user.dto_helper.UserDtoHelper.generateUserInformationResponse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HomeRepository repository;

    @Autowired
    private LocationServiceImpl locationService;

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

    private String token;

    @BeforeEach
    public void setUp() {
        token = "Bearer your-jwt-token";
        when(userService.findByEmail(anyString())).thenReturn(generateUserInformationResponse());
    }

    @Test
    @WithMockUser
    public void 집_게시글_생성_테스트() throws Exception {
        // given
        HomeGeneratorRequest homeGeneratorRequest = generateHomeGeneratorRequest();
        MockMultipartFile jsonFile = new MockMultipartFile("homeGeneratorRequest", "", "application/json",
                objectMapper.writeValueAsBytes(homeGeneratorRequest));
        MockMultipartFile image1 = new MockMultipartFile("images", "image1.jpg", "image/jpeg", "image1".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("images", "image2.jpg", "image/jpeg", "image2".getBytes());

        // when
        mockMvc.perform(MockMvcRequestBuilders.multipart(HOMES_BASE_URL)
                        .file(jsonFile)
                        .file(image1)
                        .file(image2)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
