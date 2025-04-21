package com.core.chat.dto;


import lombok.Builder;
import lombok.Getter;

/**
 * 채팅 방 생성 요청 DTO
 */
@Getter
@Builder
public class DirectMessageApplicationRequest {
    private Long senderId;
    private String message;
    private Long receiverId;
    private Long roomId;
}
