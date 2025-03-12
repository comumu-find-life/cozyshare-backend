package com.core.domain.chat.model;

import com.core.domain.deal.model.DealState;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chat_message")
public class DirectMessage {

    @Id
    @Column(name = "direct_message_id", nullable = false)
    private String id;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

    @Column(name = "deal_id", nullable = true)
    private Long dealId;

    /**
     * 0 --> 일반 채팅 메시지
     * 1 --> 안전거래 시작 폼
     * 2 --> 안전거래 진행 중 품
     * 3 --> 안전거래 완료 폼
     * 4 --> 안전거래 취소 폼
     */
    @Column(name = "is_deal", nullable = true)
    private int isDeal;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(name = "deal_state", nullable = true)
    private DealState dealState;

}
