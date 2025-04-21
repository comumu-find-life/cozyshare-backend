package com.core.deal.dto;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProtectedDealOverViewResponse {

    private Long id;

    private LocalDate createDate;
    // 거래금액
    private double deposit;

}
