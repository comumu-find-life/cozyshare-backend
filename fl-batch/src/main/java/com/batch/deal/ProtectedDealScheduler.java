package com.batch.deal;

import com.core.domain.deal.model.ProtectedDeal;
import com.core.domain.deal.repository.ProtectedDealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProtectedDealScheduler {

    private final ProtectedDealRepository protectedDealRepository;
    private final DealCompletionService dealCompletionService;
    private final NotificationService notificationService;


    @Scheduled(cron = "0 0 8 * * ?", zone = "Australia/Sydney")
    @Transactional
    public void processTodayDeals() {
        List<ProtectedDeal> todayDeals = protectedDealRepository.findAll().stream()
                .filter(ProtectedDeal::isDealToday)
                .toList();
        todayDeals.forEach(deal -> {
            try {
                notificationService.sendCompleteDealNotification(deal);
            } catch (Exception e) {
                handleError(e);
            }
        });
    }

    /**
     * 매일 아침 7시에 실행되어 거래 완료 가능한 거래를 처리합니다.
     */
    @Scheduled(cron = "0 0 7 * * ?")
    public void processPassDealDate() {
        List<ProtectedDeal> todayDeals = protectedDealRepository.findAll().stream()
                .filter(ProtectedDeal::isPossibleAutoComplete)
                .toList();

        todayDeals.forEach(deal -> {
            try {
                String providerFcmToken = dealCompletionService.completeDeal(deal);
                notificationService.sendAutoCompleteDealNotification(providerFcmToken);
            } catch (Exception e) {
                handleError(e);
            }
        });
    }

    private void handleError(Exception e) {
        e.printStackTrace();
    }
}