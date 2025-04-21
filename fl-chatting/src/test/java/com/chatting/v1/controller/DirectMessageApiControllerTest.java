package com.chatting.v1.controller;

import com.chatting.v1.config.ChattingTestApplication;
import com.core.chat.repository.DirectMessageRepository;
import com.core.chat.repository.DirectMessageRoomRepository;
import com.core.user.model.User;
import com.core.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.chatting.v1.constants.ApiUrlConstants.*;
import static com.core.chat.entity_helper.DirectMessageHelper.generateDirectMessage;
import static com.core.chat.dto_helper.DirectMessageDtoHelper.generateDirectMessageApplicationRequest;
import static com.core.chat.entity_helper.DirectMessageRoomHelper.generateDirectMessageRoom;
import static com.core.user.entity_helper.UserHelper.generateUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ChattingTestApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class DirectMessageApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DirectMessageRoomRepository directMessageRoomRepository;

    @Autowired
    private DirectMessageRepository directMessageRepository;

    @Autowired
    private UserRepository userRepository;

    private User sender;

    private User receiver;

    @BeforeEach
    void setInit() {
        directMessageRoomRepository.deleteAll();
        mongoTemplate.getDb().drop();
        sender = userRepository.save(generateUser(1L));
        receiver = userRepository.save(generateUser(2L));
    }

    @Test
    void 첫_채팅전송_후_채팅방_생성() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.post(DM_SEND_FIRST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generateDirectMessageApplicationRequest(sender.getId(), receiver.getId(), "message", 1L)))
                )
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        assertThat(directMessageRoomRepository.findAll().size()).isEqualTo(1);
        assertThat(directMessageRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void 두_사람이_주고받은_채팅내역을_모두_조회한다() throws Exception {
        //given
        directMessageRepository.save(generateDirectMessage("message", receiver.getId(), sender.getId(), LocalDateTime.now(), false));
        directMessageRepository.save(generateDirectMessage("message", receiver.getId(), sender.getId(), LocalDateTime.now(), false));
        directMessageRepository.save(generateDirectMessage("message", receiver.getId(), sender.getId(), LocalDateTime.now(), false));

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get(DM_HISTORY_URL)
                        .param("user1Id", String.valueOf(sender.getId()))
                        .param("user2Id", String.valueOf(receiver.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.length()").value(3));
    }

    @Test
    void 자신의_채팅방_정보를_모두_조회한다() throws Exception {
        //given
        User receiver2 = userRepository.save(generateUser(3L));
        directMessageRoomRepository.save(generateDirectMessageRoom(sender, receiver));
        directMessageRoomRepository.save(generateDirectMessageRoom(sender, receiver2));

        directMessageRepository.save(generateDirectMessage("message", receiver.getId(), sender.getId(), LocalDateTime.now(), false));
        directMessageRepository.save(generateDirectMessage("message", receiver2.getId(), sender.getId(), LocalDateTime.now(), false));
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get(DM_FIND_ALL_ROOMS, sender.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.length()").value(2));
    }
}
