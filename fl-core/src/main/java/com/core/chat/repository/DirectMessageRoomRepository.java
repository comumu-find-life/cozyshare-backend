package com.core.chat.repository;

import com.core.chat.model.DirectMessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectMessageRoomRepository extends JpaRepository<DirectMessageRoom, Long>, CustomDirectMessageRoomRepository {
}
