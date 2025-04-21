package com.core.user.repository;

import com.core.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByRefreshToken(String refreshToken);

    @Query("SELECT u FROM User u WHERE u.id = :senderId OR u.id = :receiverId " +
            "ORDER BY CASE WHEN u.id = :senderId THEN 0 ELSE 1 END")
    List<User> findSenderAndReceiver(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

}
