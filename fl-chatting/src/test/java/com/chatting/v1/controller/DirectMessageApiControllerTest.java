package com.chatting.v1.controller;

import com.chatting.ChattingApplication;
import com.chatting.v1.config.ChattingTestApplication;
import com.core.domain.home.service.LocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infra.file.FileHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = ChattingTestApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DirectMessageApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @MockBean
    private FileHelper fileHelper;


    @Test
    void test(){
        System.out.println("call");
        System.out.println("✅ Embedded Mongo 연결 성공: " + mongoTemplate.getDb().getName());
    }

}
