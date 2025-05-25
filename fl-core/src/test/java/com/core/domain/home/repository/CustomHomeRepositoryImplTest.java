package com.core.domain.home.repository;

import com.core.home.dto.HomeInformationResponse;
import com.core.home.dto.HomeOverviewResponse;
import com.core.home.model.Home;
import com.core.user.model.User;
import com.core.user.repository.UserRepository;
import com.core.config.TestConfig;
import com.core.home.repository.HomeRepository;
import com.core.utils.TimerAop;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.core.home.entity_helper.HomeHelper.generateHome;
import static com.core.user.entity_helper.UserHelper.generateUser;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class, TimerAop.class})
public class CustomHomeRepositoryImplTest {

    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 사용자_정보가_포함된_집_게시글을_조회한다() {
        //given
        User user = userRepository.save(generateUser(1L));
        Home home = homeRepository.save(generateHome(user.getId()));

        //when
        Optional<HomeInformationResponse> homeAndUserById = homeRepository.findHomeInformationById(home.getId());

        //then
        assertThat(homeAndUserById).isPresent();
        assertThat(homeAndUserById.get().getProviderName()).isEqualTo(user.getNickname());
    }

    @Test
    void 여러개의_집_id를_이용해_조회한다() {
        //given
        User user = userRepository.save(generateUser(1L));
        Home home1 = homeRepository.save(generateHome(user.getId()));
        Home home2 = homeRepository.save(generateHome(user.getId()));
        homeRepository.save(generateHome(user.getId()));

        //when
        List<HomeOverviewResponse> byHomeIds = homeRepository.findByHomeIds(List.of(home1.getId(), home2.getId()));

        //then
        assertThat(byHomeIds.size()).isEqualTo(2);
    }

    @Test
    void 판매중인_집_게시글을_모두_조회한다() {
        //given
        User user = userRepository.save(generateUser(1L));
        homeRepository.save(generateHome(user.getId()));
        homeRepository.save(generateHome(user.getId()));

        //when
        List<HomeOverviewResponse> allSellHome = homeRepository.findAllSellHome();

        //then
        assertThat(allSellHome.size()).isEqualTo(2);
    }

    @Test
    void 도시_이름으로_판매중인_집_게시글을_조회한다(){
        //given
        User user = userRepository.save(generateUser(1L));
        homeRepository.save(generateHome(user.getId()));

        //when
        List<Home> homes = homeRepository.findByCity("city name");

        //then
        assertThat(homes.size()).isEqualTo(1);
    }

    @Test
    void 자신의_집_게시글_모두_조회한다() {
        //given
        User user = userRepository.save(generateUser(1L));
        homeRepository.save(generateHome(user.getId()));
        homeRepository.save(generateHome(user.getId()));

        //when
        List<Home> byUserId = homeRepository.findByUserId(user.getId());

        //then
        assertThat(byUserId.size()).isEqualTo(2);
    }

    @Test
    void 집_페이징_조회_테스트() {
        //when
        long start = System.currentTimeMillis(); // 시작 시간 측정
        List<HomeOverviewResponse> sellHomePage = homeRepository.findSellHomePage(PageRequest.of(1999,15));
        long end = System.currentTimeMillis(); // 종료 시간 측정

        //then
        System.out.println("findSellHomePage 실행 시간: " + (end - start) + "ms");
        Assertions.assertThat(sellHomePage.size()).isEqualTo(15);
    }
}
