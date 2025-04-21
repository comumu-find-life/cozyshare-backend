package com.core.user.service;

import com.core.user.dto.WithDrawHistoryResponse;
import com.core.user.model.ChargeType;
import com.core.user.model.PointHistory;
import com.core.user.model.UserAccount;
import com.core.user.repository.UserAccountRepository;
import com.infra.utils.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.infra.exception.ExceptionMessages.NOT_EXIST_USER_ID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPaymentService {

    private final UserAccountRepository userAccountRepository;
    @Value("${admin.token}")
    private String secretToken;
    /**
     * 환전 완료
     */
    @Transactional
    public void completeWithDraw(Long userAccountId, Long pointHistoryId, String token) {
        if (!token.equals(secretToken)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(userAccountId), NOT_EXIST_USER_ID);
        PointHistory pointHistory = userAccount.getChargeHistories().stream()
                .filter(history -> history.getId() == pointHistoryId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
        pointHistory.setChargeType(ChargeType.WITHDRAW);
    }

    public List<WithDrawHistoryResponse> findWithDraws() {
        return userAccountRepository.findAll().stream()
                .flatMap(userAccount -> userAccount.getChargeHistories().stream()
                        .filter(pointHistory -> pointHistory.getChargeType().equals(ChargeType.APPLY_WITHDRAW))
                        .map(pointHistory -> WithDrawHistoryResponse.builder()
                                .userAccountId(userAccount.getUserId())
                                .pointHistoryId(pointHistory.getId())
                                .paypalInformation(userAccount.getPaypalInformation())
                                .chargeAmount(pointHistory.getChargeAmount())
                                .chargeType(pointHistory.getChargeType())
                                .depositorName(userAccount.getDepositorName())
                                .historyDateTime(pointHistory.getHistoryDateTime())
                                .build()))
                .collect(Collectors.toList());
    }
}
