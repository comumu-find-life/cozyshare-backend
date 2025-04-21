package com.core.user.model;

import com.infra.exception.custom.InsufficientPointsException;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_account")
public class UserAccount {

    private static final String ERROR_NOT_ENOUGH_POINT = "포인트가 부족합니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_account_id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "point", nullable = true)
    private double point;

    @Column(name = "depositor_name", nullable = true)
    private String depositorName;

    @Column(name = "paypal_information", nullable = true)
    private String paypalInformation;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PointHistory> chargeHistories;

    public void registerPointChargeHistory(double chargeAmount, ChargeType chargeType) {
        PointHistory history = PointHistory.createHistory(this, chargeAmount, chargeType);
        chargeHistories.add(history);
    }

    public void validatePointsSufficiency(double amount) throws InsufficientPointsException {
        if (!isEnoughPoint(amount)) {
            throw new InsufficientPointsException(ERROR_NOT_ENOUGH_POINT);
        }
    }

    public boolean isEnoughPoint(double amount){
        return this.point - amount >= 0;
    }

    public void decreasePoint(double point) {
        this.point -= point;
    }

    public void increasePoint(double point) {
        this.point += point;
    }
}
