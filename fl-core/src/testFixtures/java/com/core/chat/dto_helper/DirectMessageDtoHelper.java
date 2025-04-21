package com.core.chat.dto_helper;

import com.core.chat.dto.*;

import java.time.LocalDateTime;

public class DirectMessageDtoHelper {

    public static DirectMessageApplicationRequest generateDirectMessageApplicationRequest(Long senderId, Long receiverId, String message, Long roomId){
        return DirectMessageApplicationRequest.builder()
                .senderId(senderId)
                .message(message)
                .receiverId(receiverId)
                .roomId(roomId)
                .build();
    }

    public static DirectMessageRequest generateDirectMessageRequest(Long senderId, Long receiverId, Long roomId, String message){
        return DirectMessageRequest.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .roomId(roomId)
                .message(message)
                .isDeal(0)
                .dealId(null)
                .dealState(null)
                .imageUrl(null)
                .build();
    }

    public static DirectMessageReadRequest generateDirectMessageReadRequest(Long senderId, Long receiverId){
        return DirectMessageReadRequest.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
    }

    public static DirectMessageTotalRequest generateDirectMessageTotalRequest(Long homeId, Long receiverId, Long roomId, Long senderId) {
        return DirectMessageTotalRequest.builder()
                .homeId(homeId)
                .receiverId(receiverId)
                .roomId(roomId)
                .senderId(senderId)
                .build();
    }

    public static DirectMessageResponse generateDirectMessageResponse(Long senderId, Long receiverId, String message, LocalDateTime sentAt){
        return DirectMessageResponse.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .dealId(null)
                .message(message)
                .isDeal(1)
                .dealState(null)
                .sentAt(sentAt)
                .build();

    }
}
