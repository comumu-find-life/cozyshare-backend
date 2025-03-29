package com.core.chat.dto_helper;

import com.core.domain.chat.dto.DirectMessageTotalRequest;

public class DirectMessageDtoHelper {

    public static DirectMessageTotalRequest generateDirectMessageTotalRequest(Long homeId, Long receiverId, Long roomId, Long senderId) {
        return DirectMessageTotalRequest.builder()
                .homeId(homeId)
                .receiverId(receiverId)
                .roomId(roomId)
                .senderId(senderId)
                .build();
    }
}
