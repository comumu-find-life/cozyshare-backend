package com.core.domain.chat;

import com.core.config.EmbeddedMongoTestConfig;
import com.core.config.TestConfig;
import com.core.domain.chat.model.DirectMessage;
import com.core.domain.chat.repository.CustomDirectMessageRepository;
import com.core.domain.chat.repository.DirectMessageRepository;
import com.core.domain.chat.repository.impl.CustomDirectMessageRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;


@DataMongoTest
@ActiveProfiles("test")
@Import({CustomDirectMessageRepositoryImpl.class, EmbeddedMongoTestConfig.class})
public class CustomDirectMessageRepositoryTest {

    @Autowired
    private DirectMessageRepository directMessageRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testMongoConnection() {
        System.out.println("Mongo DB name: " + mongoTemplate.getDb().getName());
    }

    @Test
    void test(){
        DirectMessage message1 = DirectMessage.builder()
                .message("message1")
                .receiverId(1L)
                .senderId(2L)
                .sentAt(LocalDateTime.now())
                .isRead(false)
                .build();
        directMessageRepository.save(message1);

    }
}