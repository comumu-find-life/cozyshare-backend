package com.core.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DirectMessageTotalRequest {
    private Long senderId;
    private Long receiverId;
    private Long homeId;
    private Long roomId;
}
