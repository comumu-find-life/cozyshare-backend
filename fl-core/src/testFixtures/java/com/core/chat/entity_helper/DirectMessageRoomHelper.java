package com.core.chat.entity_helper;

import com.core.chat.model.DirectMessageRoom;
import com.core.user.model.User;

public class DirectMessageRoomHelper {
    public static DirectMessageRoom generateDirectMessageRoom(User user1, User user2){
        return DirectMessageRoom.builder()
                .user1(user1)
                .user2(user2)
                .progressHomeId(1L)
                .build();
    }
}
