package com.core.chat.entity_helper;

import com.core.chat.model.DirectMessage;
import com.core.deal.model.DealState;

import java.time.LocalDateTime;

public class DirectMessageHelper {

    public static DirectMessage generateDirectMessage(String message, Long receiverId, Long senderId, LocalDateTime sendAt, boolean isRead){
        return DirectMessage.builder()
                .message(message)
                .receiverId(receiverId)
                .senderId(senderId)
                .sentAt(sendAt)
                .isRead(isRead)
                .build();
    }

    public static DirectMessage generateDirectMessageByDeal(Long receiverId, Long senderId, LocalDateTime sendAt, boolean isRead, Long dealId, int isDeal, DealState dealState){
        return DirectMessage.builder()
                .message("message")
                .receiverId(receiverId)
                .senderId(senderId)
                .sentAt(sendAt)
                .isRead(isRead)
                .dealId(dealId)
                .isDeal(isDeal)
                .dealState(dealState)
                .build();
    }
}
