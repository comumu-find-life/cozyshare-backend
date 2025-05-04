package com.core.deal.service;

import com.core.deal.dto.ProtectedDealGeneratorRequest;
import com.core.deal.dto.ProtectedDealResponse;
import com.core.deal.model.DealState;
import com.core.deal.model.ProtectedDeal;
import com.core.deal.repository.ProtectedDealRepository;
import com.core.mapper.ProtectedDealMapper;
import com.core.deal.dto.ProtectedDealGeneratorResponse;
import com.infra.exception.custom.NotMatchGetterException;
import com.infra.fcm.NotificationService;
import com.infra.fcm.NotificationState;
import com.core.home.model.Home;
import com.core.home.model.HomeStatus;
import com.core.home.repository.HomeRepository;
import com.infra.utils.OptionalUtil;
import com.core.user.model.User;
import com.core.user.model.UserAccount;
import com.core.user.repository.UserAccountRepository;
import com.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.infra.exception.ExceptionMessages.*;

@Transactional
@Service
@RequiredArgsConstructor
public class ProtectedDealService {

    private final NotificationService notificationHelper;
    private final ProtectedDealRepository protectedDealRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserRepository userRepository;
    private final HomeRepository homeRepository;
    private final ProtectedDealMapper mapper;

    /**
     * 안전 거래 생성 메서드 (by 임대인)
     */
    public ProtectedDealGeneratorResponse saveProtectedDeal(ProtectedDealGeneratorRequest request) {
        ProtectedDeal deal = mapper.toEntity(request);
        Long dealId = protectedDealRepository.save(deal).getId();
        ProtectedDealGeneratorResponse protectedDealGeneratorResponse = mapper.toGeneratorResponse(dealId);
        return protectedDealGeneratorResponse;
    }

    /**
     * 내 안전 거래 조회 메서드
     */
    public List<ProtectedDealResponse> findAllByUserId(Long userId) {
        List<ProtectedDeal> allByUserId = protectedDealRepository.findAllByUserId(userId);
        List<ProtectedDealResponse> response = new ArrayList<>();
        allByUserId.stream()
                .forEach(protectedDeal -> {
                    Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(protectedDeal.getHomeId()), NOT_EXIST_HOME_ID);
                    response.add(mapper.toResponse(protectedDeal, home));
                });
        return response;
    }

    /**
     * 안전거래 조회 메서드 by 임차인
     */
    public List<ProtectedDealResponse> findProtectedDeal(Long getterId, Long providerId, Long homeId, Long dmId) {
        return protectedDealRepository.findProtectedDealsByFilters(getterId, providerId, homeId, dmId);
    }

    /**
     * 안전 거래 수락 by 임차인
     */
    @CacheEvict(value = "homeOverviewCache", key = "'allHomes'", allEntries = true)
    @Transactional
    public void acceptProtectedDeal(Long dealId, Long getterId)  {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), NOT_EXIST_DEAL_ID);
        validateMatchDealOwner(protectedDeal, getterId);
        User getter = OptionalUtil.getOrElseThrow(userRepository.findById(getterId), NOT_EXIST_USER_ID);
        UserAccount userAccount = userAccountRepository.findByIdWithLock(getter.getId()).get();
        userAccount.validatePointsSufficiency(protectedDeal.calculateTotalPrice());
        userAccount.decreasePoint(protectedDeal.calculateTotalPrice());
        protectedDeal.setDealState(DealState.ACCEPT_DEAL);
        protectedDeal.getProtectedDealDateTime().setStartAt(LocalDateTime.now());
    }

    /**
     * 거래 완료 메서드 by 임차인
     */
    @CacheEvict(value = "homeOverviewCache", key = "'allHomes'", allEntries = true)
    @Transactional
    public void completeDeal(Long dealId, Long getterId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), NOT_EXIST_DEAL_ID);
        validateMatchDealOwner(protectedDeal, getterId);

        User provider = OptionalUtil.getOrElseThrow(userRepository.findById(protectedDeal.getProviderId()), NOT_EXIST_USER_ID);
        UserAccount providerAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByIdWithLock(protectedDeal.getProviderId()), NOT_EXIST_ACCOUNT_ID);

        providerAccount.increasePoint(protectedDeal.getDeposit());
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(protectedDeal.getHomeId()), NOT_EXIST_HOME_ID);
        home.setHomeStatus(HomeStatus.SOLD_OUT);
        protectedDeal.getProtectedDealDateTime().setCompleteAt(LocalDateTime.now());
        protectedDeal.setDealState(DealState.COMPLETE_DEAL);
        notificationHelper.sendNotification(NotificationState.SAVE, provider.getFcmToken(), "The transaction has been completed", "the deposit has been paid. Please check it on MyPage.");
    }

    /**
     * 안전 거래 생성 전 취소 메서드
     */
    @Transactional
    public void cancelBeforeDeal(Long dealId, Long getterId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), NOT_EXIST_DEAL_ID);
        validateMatchDealOwner(protectedDeal, getterId);
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(protectedDeal.getHomeId()), NOT_EXIST_HOME_ID);
        home.setHomeStatus(HomeStatus.FOR_SALE);
        protectedDeal.getProtectedDealDateTime().setCancelAt(LocalDateTime.now());
        protectedDeal.setDealState(DealState.CANCEL_BEFORE_DEAL);
    }

    /**
     * 안전 거래 생성 후 취소 메서드 (by 임차인)
     */
    @Transactional
    public void cancelAfterDeal(Long dealId, Long getterId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), NOT_EXIST_DEAL_ID);
        validateMatchDealOwner(protectedDeal, getterId);
        UserAccount getterAccount = userAccountRepository.findByIdWithLock(protectedDeal.getGetterId()).get();
        getterAccount.increasePoint(protectedDeal.getDeposit());
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(protectedDeal.getHomeId()), NOT_EXIST_HOME_ID);
        home.setHomeStatus(HomeStatus.FOR_SALE);
        protectedDeal.getProtectedDealDateTime().setCancelAt(LocalDateTime.now());
        protectedDeal.setDealState(DealState.CANCEL_DURING_DEAL);
    }

    private void validateMatchDealOwner(ProtectedDeal protectedDeal, Long getterId){
        if(protectedDeal.getGetterId() != getterId) {
            throw new NotMatchGetterException("안전거래 임차인과 요청을 보낸 사용자가 다릅니다.");
        }
    }
}
