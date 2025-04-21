package com.core.user.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 충전 금액
    private double chargeAmount;

    // 충전 일자
    private LocalDateTime historyDateTime;

    @Enumerated(EnumType.STRING)
    private ChargeType chargeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    static PointHistory createHistory(UserAccount userAccount, double chargeAmount, ChargeType chargeType) {
        PointHistory history = new PointHistory();
        history.setUserAccount(userAccount);
        history.setChargeAmount(chargeAmount);
        history.setHistoryDateTime(LocalDateTime.now());
        history.setChargeType(chargeType);
        return history;
    }
}
