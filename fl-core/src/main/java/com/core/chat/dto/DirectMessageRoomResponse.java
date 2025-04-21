package com.core.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DirectMessageRoomResponse {

    private Long id;
    private Long user1Id;
    private Long user2Id;
}
