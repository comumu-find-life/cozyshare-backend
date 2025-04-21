package com.core.domain.deal.repository;

import com.core.config.TestConfig;
import com.core.deal.dto.ProtectedDealResponse;
import com.core.deal.model.ProtectedDeal;
import com.core.deal.repository.ProtectedDealRepository;
import com.core.home.model.Home;
import com.core.home.repository.HomeRepository;
import com.core.utils.TimerAop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.core.deal.entity_helper.ProtectedDealHelper.generateProtectedDeal;
import static com.core.deal.entity_helper.ProtectedDealHelper.generateProtectedDealWithUserIds;
import static com.core.home.entity_helper.HomeHelper.generateHome;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class, TimerAop.class})
public class CustomProtectedDealRepositoryTest {

    @Autowired
    private ProtectedDealRepository protectedDealRepository;

    @Autowired
    private HomeRepository homeRepository;


    @Test
    void 자신의_모든_안전거래_내역을_조회한다() {
        //given
        ProtectedDeal protectedDeal1 = protectedDealRepository.save(generateProtectedDeal(1L));
        protectedDealRepository.save(generateProtectedDeal(2L));

        //when
        List<ProtectedDeal> allByUserId = protectedDealRepository.findAllByUserId(protectedDeal1.getGetterId());

        //then
        assertThat(allByUserId.size()).isEqualTo(2);
    }

    @Test
    void 조건에_맞는_안전거래를_모두_조회한다() {
        //given
        Home home = homeRepository.save(generateHome(1L));
        ProtectedDeal protectedDeal1 = protectedDealRepository.save(generateProtectedDealWithUserIds(home.getId(), 2L, 1L));

        //when
        List<ProtectedDealResponse> protectedDealsByFilters = protectedDealRepository.findProtectedDealsByFilters(protectedDeal1.getGetterId(), protectedDeal1.getProviderId(), protectedDeal1.getHomeId(), protectedDeal1.getDmId());

        //then
        assertThat(protectedDealsByFilters.size()).isEqualTo(1);
    }
}
