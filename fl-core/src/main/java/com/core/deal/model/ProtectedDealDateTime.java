package com.core.deal.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProtectedDealDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "protected_deal_time_id")
    private Long id;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "cancel_at")
    private LocalDateTime cancelAt;

    @Column(name = "complete_at")
    private LocalDateTime completeAt;

    @Column(name = "deal_at")
    private LocalDateTime dealAt;

    public boolean isToday() {
        LocalDate today = LocalDate.now();
        return dealAt != null && dealAt.toLocalDate().isEqual(today);
    }

    public boolean isFiveDaysPassed() {
        LocalDate today = LocalDate.now();
        return dealAt != null && dealAt.toLocalDate().isBefore(today.minusDays(5));
    }
}
