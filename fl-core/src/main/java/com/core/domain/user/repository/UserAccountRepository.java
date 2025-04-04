package com.core.domain.user.repository;

import com.core.domain.user.model.UserAccount;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM UserAccount u WHERE u.userId = :userId")
    @QueryHints({
            @QueryHint(name = "jakarta.persistence.lock.timeout", value = "8000")
    })
    Optional<UserAccount> findByIdWithLock(@Param("userId") Long userId);
}
