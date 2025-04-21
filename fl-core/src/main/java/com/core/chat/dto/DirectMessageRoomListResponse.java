package com.core.chat.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * todo
 */
@Getter
@Builder
public class DirectMessageRoomListResponse {
    private Long id;
    private Long otherUserId;
    private String userNickname;
    private String userProfileUrl;
    private Long progressHomeId;
    private String lastMessage;
    private LocalDateTime lastSendDateTime;
    private int notReadCount;
}
