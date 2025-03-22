package com.core.domain.home.repository;

import com.core.domain.home.model.Home;
import com.core.domain.home.repository.HomeRepository;
import com.core.domain.user.model.User;
import com.core.domain.user.repository.UserRepository;
import com.core.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static com.core.home.HomeHelper.generateHome;
import static com.core.user.UserBuilder.generateUser;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
public class CustomHomeRepositoryImplTest {

    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void home_페이징_조회_테스트() {
        //given
        User user = userRepository.save(generateUser(1L));
        Home save = homeRepository.save(generateHome(user.getId()));

        homeRepository.findHomeAndUserById(save.getId());
    }

}
