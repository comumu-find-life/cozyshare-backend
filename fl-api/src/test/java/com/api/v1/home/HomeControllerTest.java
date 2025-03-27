package com.api.v1.home;

import com.api.auth.service.JwtService;
import com.core.domain.home.dto.HomeGeneratorRequest;
import com.core.domain.home.dto.HomeInformationResponse;
import com.core.domain.home.dto.HomeOverviewResponse;
import com.core.domain.home.dto.HomeUpdateRequest;
import com.core.domain.home.model.Home;
import com.core.domain.home.model.LatLng;
import com.core.domain.home.repository.HomeRepository;
import com.core.domain.home.service.LocationServiceImpl;
import com.core.domain.user.model.User;
import com.core.domain.user.repository.UserRepository;
import com.core.domain.user.service.UserService;
import com.core.mapper.HomeMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infra.utils.SuccessResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

import java.util.List;
import java.util.Optional;

import static com.api.v1.constants.ApiUrlConstants.*;
import static com.core.home.dto_helper.HomeDtoHelper.*;
import static com.core.home.entity_helper.HomeHelper.generateHome;
import static com.core.user.dto_helper.UserDtoHelper.generateUserInformationResponse;
import static com.core.user.entity_helper.UserHelper.generateUser;
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
    private HomeRepository homeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private HomeMapper homeMapper;

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
    public void 집_게시글을_저장한다() throws Exception {
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

    @Test
    public void 집_주소를_조회한다() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.post(HOMES_VALIDATE_ADDRESS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generateHomeAddressGeneratorRequest()))
                        .header(HttpHeaders.AUTHORIZATION, token))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, SuccessHomeMessages.ADDRESS_VALIDATION_SUCCESS, new LatLng(-37.81314649999999,144.9684679)))));

    }

    @Test
    public void 자신의_모든_집_게시글을_조회한다() throws Exception {
        //given
        User user = userRepository.save(generateUser(1L));
        Home saveHome1 = homeRepository.save(generateHome(user.getId()));
        Home saveHome2 = homeRepository.save(generateHome(user.getId()));

        List<HomeOverviewResponse> resultHomes = List.of(homeMapper.toSimpleHomeDto(saveHome1, user), homeMapper.toSimpleHomeDto(saveHome2, user));

        mockMvc.perform(MockMvcRequestBuilders.get(HOMES_FIND_BY_USER_ID, user.getId())
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, SuccessHomeMessages.USER_POSTS_RETRIEVE_SUCCESS, resultHomes))));
    }

    @Test
    public void 집_상세_정보를_조회한다() throws Exception {
        //given
        User user = userRepository.save(generateUser(1L));
        Home saveHome = homeRepository.save(generateHome(user.getId()));
        HomeInformationResponse resultHome = homeMapper.toHomeInformation(saveHome, user);

        mockMvc.perform(MockMvcRequestBuilders.get(HOMES_FIND_BY_ID, saveHome.getId())
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, SuccessHomeMessages.HOME_RETRIEVE_SUCCESS, resultHome))));
    }

    @Test
    public void 집_정보를_수정한다() throws Exception {
        //given
        User user = userRepository.save(generateUser(1L));
        Home home = homeRepository.save(generateHome(user.getId()));
        HomeUpdateRequest homeUpdateRequest = generateHomeUpdateRequest(home.getId());

        mockMvc.perform(MockMvcRequestBuilders.patch(HOMES_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(homeUpdateRequest))
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Optional<Home> updateHome = homeRepository.findById(home.getId());
        Assertions.assertThat(updateHome.get().getHomeInfo().getBond()).isEqualTo(100);
    }
}
