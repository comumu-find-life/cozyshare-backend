package com.core.domain.user.repository;


import com.core.config.TestConfig;
import com.core.domain.user.model.UserAccount;
import com.core.utils.TimerAop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.core.user.entity_helper.UserHelper.generateUserAccount;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class, TimerAop.class})
public class UserAccountTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void 비관적_락_계죄_조회_테스트() throws InterruptedException {
        //given
        Long userId = 1L;
        userAccountRepository.save(generateUserAccount(userId, 1000));

        int threadCount = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        Runnable readTask = () -> {
            try {
                userAccountRepository.findByIdWithLock(userId);
                System.out.println(Thread.currentThread().getName() + " - Locked user account: " + userId);
                Thread.sleep(1000); // 읽기 락 유지
            } catch (Exception e) {
                System.err.println(Thread.currentThread().getName() + " - Exception: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        };

        Runnable writeTask = () -> {
            try {
                entityManager.getTransaction().begin();
                UserAccount userAccount = entityManager.find(UserAccount.class, userId);
                userAccount.setPoint(userAccount.getPoint() + 500);
                entityManager.merge(userAccount);
                entityManager.getTransaction().commit();
                System.out.println(Thread.currentThread().getName() + " - Updated user account: " + userId);
            } catch (Exception e) {
                System.err.println(Thread.currentThread().getName() + " - Update Exception: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        };

        executorService.execute(readTask); // 먼저 읽기 락을 건다
        Thread.sleep(200); // 약간의 시간 차이를 둠
        executorService.execute(writeTask); // 이후 업데이트 시도

        latch.await(3, TimeUnit.SECONDS);
        executorService.shutdown();

        assertThat(userAccountRepository.findByUserId(userId).get().getPoint()).isEqualTo(1000);
    }
}
