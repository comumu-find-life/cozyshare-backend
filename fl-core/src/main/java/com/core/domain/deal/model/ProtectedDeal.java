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

    private static final double feeRate = 0.05;

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
        return Math.round(deposit * feeRate * 100.0) / 100.0;
    }

    public boolean isDealToday(){
        return protectedDealDateTime.isToday();
    }

    public boolean isPossibleAutoComplete(){
        return protectedDealDateTime.isFiveDaysPassed() && (dealState.equals(DealState.ACCEPT_DEAL));
    }

}
