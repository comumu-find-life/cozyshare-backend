package com.core.deal.model;

import lombok.Getter;

@Getter
public enum DealState {
    NONE,
    REQUEST_DEAL, // 거래 요청
    ACCEPT_DEAL, // 거래 수락
    CANCEL_BEFORE_DEAL, // 거래 수락 전 취소(Deposit 환불 x)
    CANCEL_DURING_DEAL, // 거래 수락 후 취소 (Deposit 환불)
    COMPLETE_DEAL, // 거래 완료
}
