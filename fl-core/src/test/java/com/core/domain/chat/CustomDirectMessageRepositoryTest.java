package com.core.domain.chat;

import com.core.config.TestConfig;
import com.core.domain.chat.repository.DirectMessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataMongoTest
@ActiveProfiles("test")
public class CustomDirectMessageRepositoryTest {

    @Autowired
    private DirectMessageRepository directMessageRepository;

    @Test
    void testSaveAndFindDirectMessage() {
    }
}