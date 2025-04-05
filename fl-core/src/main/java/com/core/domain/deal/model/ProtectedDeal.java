package com.core.domain.deal.model;

import com.core.domain.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
