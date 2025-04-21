package com.chatting.v1.service;

import com.core.chat.dto.DirectMessageRequest;
import com.core.chat.model.DirectMessageRoom;
import com.core.user.model.User;

public class DirectMessageHelper {

    public static DirectMessageRequest createDirectMessageRequest(final String message, final Long senderId, final Long receiverId){
        return  DirectMessageRequest.builder()
                .message(message)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
    }

    public static DirectMessageRoom createDirectMessageRoom(final User user1,  final User user2, final Long roomId){
        return DirectMessageRoom.builder()
                .user1(user1)
                .user2(user2)
                .progressHomeId(roomId)
                .build();
    }

}
