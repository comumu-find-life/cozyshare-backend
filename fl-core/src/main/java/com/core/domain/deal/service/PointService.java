package com.core.domain.deal.service;

import com.infra.utils.OptionalUtil;
import com.core.domain.user.model.ChargeType;
import com.core.domain.user.model.User;
import com.core.domain.user.model.UserAccount;
import com.core.domain.user.repository.UserAccountRepository;
import com.core.domain.user.repository.UserRepository;
import com.infra.exception.custom.InsufficientPointsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.infra.exception.ExceptionMessages.NOT_EXIST_USER_EMAIL;
import static com.infra.exception.ExceptionMessages.NOT_EXIST_USER_ID;

@Transactional
@Service
@RequiredArgsConstructor
public class PointService {

    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    public void chargePoint(final String email, final double point) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIST_USER_EMAIL);
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(user.getId()), NOT_EXIST_USER_ID);
        userAccount.increasePoint(point);
        userAccount.registerPointChargeHistory(point, ChargeType.DEPOSIT);
    }

    public void applyWithDraw(final String email, final double point) throws InsufficientPointsException {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIST_USER_EMAIL);
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(user.getId()), NOT_EXIST_USER_ID);
        userAccount.validatePointsSufficiency(point);
        userAccount.registerPointChargeHistory(point, ChargeType.APPLY_WITHDRAW);
        userAccount.decreasePoint(point);
    }
}
