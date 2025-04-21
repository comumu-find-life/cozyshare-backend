package com.core.chat.model;

import com.core.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectMessageRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "direct_message_room_id")
    private Long id;

    @JoinColumn(name = "user_1_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user1;

    @JoinColumn(name = "user_2_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user2;

    @Setter
    @Column(name = "progress_home_id")
    private Long progressHomeId;
}
