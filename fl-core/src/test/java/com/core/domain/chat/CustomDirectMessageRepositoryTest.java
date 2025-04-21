package com.core.domain.chat;

import com.core.chat.model.DirectMessage;
import com.core.chat.repository.DirectMessageRepository;
import com.core.chat.repository.impl.DirectMessageRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.core.chat.entity_helper.DirectMessageHelper.generateDirectMessage;
import static org.assertj.core.api.Assertions.assertThat;


@DataMongoTest
@ActiveProfiles("test")
@Import({DirectMessageRepositoryImpl.class})
public class CustomDirectMessageRepositoryTest {

    @Autowired
    private DirectMessageRepository directMessageRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @BeforeEach
    void tearDown() {
        mongoTemplate.getDb().drop();
    }

    @Test
    void 마지막_채팅_내역을_조회한다(){
        //given
        DirectMessage message = generateDirectMessage("message", 1L, 2L, LocalDateTime.now(), false);
        directMessageRepository.save(message);

        //when
        Optional<DirectMessage> lastMessageByUserIds = directMessageRepository.findLastMessageByUserIds(1L, 2L);

        //then
        assertThat(lastMessageByUserIds.get().getMessage()).isEqualTo("message");
    }

    @Test
    void 두사람의_채팅_내역을_모두_조회한다() {
        //given
        directMessageRepository.save(generateDirectMessage("message1", 1L, 2L, LocalDateTime.now(), false));
        directMessageRepository.save(generateDirectMessage("message2", 2L, 1L, LocalDateTime.now(), false));
        directMessageRepository.save(generateDirectMessage("message3", 1L, 2L, LocalDateTime.now(), false));

        //when
        List<DirectMessage> directMessageByUserIds1 = directMessageRepository.findDirectMessageByUserIds(1L, 2L);
        List<DirectMessage> directMessageByUserIds2 = directMessageRepository.findDirectMessageByUserIds(2L, 1L);

        //then
        Assertions.assertThat(directMessageByUserIds1.size()).isEqualTo(3);
        Assertions.assertThat(directMessageByUserIds2.size()).isEqualTo(3);
    }


    @Test
    void 읽지않은_채팅내역_갯수를_조회한다() {
        //given
        directMessageRepository.save(generateDirectMessage("message1", 1L, 2L, LocalDateTime.now(), false));
        directMessageRepository.save(generateDirectMessage("message2", 1L, 2L, LocalDateTime.now(), false));
        directMessageRepository.save(generateDirectMessage("message3", 1L, 2L, LocalDateTime.now(), false));

        //when
        int response = directMessageRepository.countNotReadMessage(1L, 2L);

        //then
        Assertions.assertThat(response).isEqualTo(3);
    }

    @Test
    void 읽지않은_채팅내역을_읽음_상태로_변경한다() {
        //given
        directMessageRepository.save(generateDirectMessage("message1", 1L, 2L, LocalDateTime.now(), false));
        directMessageRepository.save(generateDirectMessage("message2", 1L, 2L, LocalDateTime.now(), false));
        directMessageRepository.save(generateDirectMessage("message3", 1L, 2L, LocalDateTime.now(), false));

        //when
        directMessageRepository.markMessagesAsRead(1L, 2L);
        int response = directMessageRepository.countNotReadMessage(1L, 2L);

        //then
        Assertions.assertThat(response).isEqualTo(0);
    }
}