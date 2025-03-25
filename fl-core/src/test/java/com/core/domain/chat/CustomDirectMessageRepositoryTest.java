package com.core.domain.chat;

import com.core.domain.chat.model.DirectMessage;
import com.core.domain.chat.repository.DirectMessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * MongoDB 로컬 테스트를 위한 Docker 빌드 명령어
 * docker run -d --name mongodb -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=sin981023@ mongo:latest
 */
@DataMongoTest
@ActiveProfiles("test")
public class CustomDirectMessageRepositoryTest {

    @Autowired
    private DirectMessageRepository directMessageRepository;

//    @Test
//    void testSaveAndFindDirectMessage() {
//        List<DirectMessage> all = directMessageRepository.findAll();
//        System.out.println("allll");
//        System.out.println(all.size());
//
//    }
}