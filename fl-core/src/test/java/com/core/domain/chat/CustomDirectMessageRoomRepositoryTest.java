package com.core.domain.chat;

import com.core.domain.chat.model.DirectMessageRoom;
import com.core.domain.chat.repository.DirectMessageRoomRepository;
import com.core.domain.user.model.User;
import com.core.domain.user.repository.UserRepository;
import com.core.config.TestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.core.chat.DirectMessageRoomHelper.generateDirectMessageRoom;
import static com.core.user.UserBuilder.generateUser;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
public class CustomDirectMessageRoomRepositoryTest {

    @Autowired
    private DirectMessageRoomRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 두명의_사용자가_속한_채팅방을_조회한다() {
        //given
        User user1 = userRepository.save(generateUser(1L));
        User user2 = userRepository.save(generateUser(2L));
        DirectMessageRoom directMessageRoom = generateDirectMessageRoom(user1, user2);
        repository.save(directMessageRoom);

        //when
        Optional<DirectMessageRoom> byUser1IdAndUser2Id = repository.findByUser1IdAndUser2Id(user1.getId(), user2.getId());
        //then
        Assertions.assertThat(byUser1IdAndUser2Id).isNotEmpty();
    }

    @Test
    void 자신이_소속한_채팅방_조회_테스트() {
        //given
        User user1 = userRepository.save(generateUser(1L));
        User user2 = userRepository.save(generateUser(2L));
        DirectMessageRoom directMessageRoom = generateDirectMessageRoom(user1, user2);
        repository.save(directMessageRoom);
        User user4 = userRepository.save(generateUser(4L));
        DirectMessageRoom directMessageRoom2 = generateDirectMessageRoom(user1, user4);
        repository.save(directMessageRoom2);

        //when
        List<DirectMessageRoom> byUser1IdOrUser2Id = repository.findByUser1IdOrUser2Id(user1.getId());

        //then
        Assertions.assertThat(byUser1IdOrUser2Id.size()).isEqualTo(2);

    }
}
