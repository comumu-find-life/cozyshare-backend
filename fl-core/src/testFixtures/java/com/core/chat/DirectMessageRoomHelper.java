package com.core.chat;

import com.core.domain.chat.model.DirectMessageRoom;
import com.core.domain.user.model.User;

public class DirectMessageRoomHelper {
    public static DirectMessageRoom generateDirectMessageRoom(User user1, User user2){
        return DirectMessageRoom.builder()
                .user1(user1)
                .user2(user2)
                .progressHomeId(1L)
                .build();
    }
}
