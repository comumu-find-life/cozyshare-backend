package com.core.domain.user.repository;

import com.core.domain.user.model.UserAccount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT u FROM UserAccount u WHERE u.id = :userId")
    Optional<UserAccount> findByIdWithLock(@Param("userId") Long userId);
}
