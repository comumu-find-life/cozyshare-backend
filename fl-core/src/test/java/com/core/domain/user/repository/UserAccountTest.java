package com.core.domain.user.repository;

import com.core.config.TestConfig;
import com.core.domain.user.model.UserAccount;
import com.core.utils.TimerAop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Optional;
import java.util.concurrent.*;

import static com.core.user.entity_helper.UserHelper.generateUserAccount;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class, TimerAop.class})
public class UserAccountTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @PersistenceContext
    private EntityManager entityManager;

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    @BeforeEach
    void setBefore(){
        userAccountRepository.deleteAll();
    }

//    @Test
//    void 비관적_락이_잘_적용되는지_테스트() throws ExecutionException, InterruptedException {
//        executeInTransaction(() -> userAccountRepository.save(generateUserAccount(1L, 1000)));
//        CountDownLatch latch = new CountDownLatch(1);
//
//        // 첫 번째 트랜잭션: 락을 획득하고 3초 동안 대기
//        Future<Void> firstTransaction = executorService.submit(() -> {
//            executeInTransaction(() -> {
//                userAccountRepository.findByIdWithLock(1L);
//                latch.countDown();
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//            return null;
//        });
//
//        latch.await(); // 첫 번째 트랜잭션이 락을 잡을 때까지 대기
//
//        // 두 번째 트랜잭션 실행
//        Future<Long> secondTransaction = executorService.submit(() -> {
//            long startTime = System.currentTimeMillis();
//            executeInTransaction(() -> {
//                userAccountRepository.findByIdWithLock(1L);
//            });
//            long elapsedTime = System.currentTimeMillis() - startTime;
//            System.out.println("두 번째 트랜잭션 종료 - 대기 시간: " + elapsedTime + "ms");
//            return elapsedTime;
//        });
//
//        firstTransaction.get();
//        long waitingTime = secondTransaction.get();
//
//        //두 번째 트랜잭션이 최소 3초 이상 대기했는지 검증
//        assertThat(waitingTime).isGreaterThanOrEqualTo(3000);
//    }


    /**
     * 트랜잭션을 수동으로 시작하고 실행하는 메서드
     */
    private <T> T executeInTransaction(Callable<T> action) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 새로운 트랜잭션 시작
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            T result = action.call();
            transactionManager.commit(status);
            return result;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new RuntimeException(e);
        }
    }

    private void executeInTransaction(Runnable action) {
        executeInTransaction(() -> {
            action.run();
            return null;
        });
    }
}
