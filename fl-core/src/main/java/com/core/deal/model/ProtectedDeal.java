package com.core.deal.model;

import com.core.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * 안전거래 정보를 나타내는 엔티티
 */
@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "protected_deal", indexes = {
        @Index(name = "idx_getter_provider_home_dm", columnList = "getter_id, provider_id, home_id, dm_id"),
        @Index(name = "idx_home_id", columnList = "home_id"),
        @Index(name = "idx_dm_id", columnList = "dm_id")
})
public class ProtectedDeal extends BaseTimeEntity {

    // 500 AUD 이하: 수수료 9%
    private static final double FEE_RATE_UNDER_500_AUD = 0.09;

    // 500 AUD 이상: 수수료 6%
    private static final double FEE_RATE_OVER_500_AUD = 0.06;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "protected_deal_id", nullable = false)
    private Long id;

    @Column(name = "home_id", nullable = false)
    private Long homeId;

    @Column(name = "dm_id", nullable = false)
    private Long dmId;

    @Column(name = "getter_id", nullable = false)
    private Long getterId;

    @Column(name = "provider_id", nullable = false)
    private Long providerId;

    @Column(name = "deposit", nullable = false)
    private double deposit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "protected_deal_time_id")
    private ProtectedDealDateTime protectedDealDateTime;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "deal_state", nullable = false)
    private DealState dealState;

    public double calculateTotalPrice() {
        return deposit + calculateFee();
    }

    public double calculateFee() {
        if(deposit <= 500){
            return Math.round(deposit * FEE_RATE_UNDER_500_AUD * 100.0) / 100.0;
        }
        return Math.round(deposit * FEE_RATE_OVER_500_AUD * 100.0) / 100.0;
    }

    public boolean isDealToday(){
        return protectedDealDateTime.isToday();
    }

    public boolean isPossibleAutoComplete(){
        return protectedDealDateTime.isFiveDaysPassed() && (dealState.equals(DealState.ACCEPT_DEAL));
    }
}
