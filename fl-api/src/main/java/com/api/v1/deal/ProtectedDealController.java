package com.api.v1.deal;

import com.core.domain.deal.dto.ProtectedDealFindRequest;
import com.core.domain.deal.dto.ProtectedDealGeneratorRequest;
import com.core.domain.deal.dto.ProtectedDealGeneratorResponse;
import com.core.domain.deal.dto.ProtectedDealResponse;
import com.core.domain.user.dto.UserInformationResponse;
import com.core.domain.user.service.UserService;
import com.infra.utils.SuccessResponse;
import com.core.domain.deal.service.ProtectedDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.api.v1.constants.ApiUrlConstants.*;
import static com.api.v1.constants.ResponseMessage.*;

/**
 * 클라이언트가 사용할 안전거래 API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping
public class ProtectedDealController {

    private final ProtectedDealService protectedDealService;
    private final UserService userService;

    @PostMapping(DEALS_READ)
    public ResponseEntity<?> findProtectedDeal(@RequestBody final ProtectedDealFindRequest request) {
        List<ProtectedDealResponse> protectedDealResponse = protectedDealService.findProtectedDeal(request.getGetterId(), request.getProviderId(), request.getHomeId(), request.getDmId());
        SuccessResponse response = new SuccessResponse(true, FIND_PROTECTED_DEAL, protectedDealResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(DEALS_FIND_ALL_BY_USER_ID)
    public ResponseEntity<?> findAllByUserId(@PathVariable final Long userId){
        List<ProtectedDealResponse> allByUserId = protectedDealService.findAllByUserId(userId);
        SuccessResponse response = new SuccessResponse(true, FIND_MY_PROTECTED_DEAL, allByUserId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(DEALS_SAVE)
    public ResponseEntity<?> saveProtectedDeal(@RequestBody final ProtectedDealGeneratorRequest request)  {
        ProtectedDealGeneratorResponse protectedDeal = protectedDealService.saveProtectedDeal(request);
        SuccessResponse response = new SuccessResponse(true, PROTECTED_DEAL, protectedDeal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(DEALS_ACCEPT_REQUEST)
    public ResponseEntity<?> acceptDeal(@PathVariable final Long dealId) {
        Long getterId = getLoggedInUserId();
        protectedDealService.acceptProtectedDeal(dealId, getterId);
        SuccessResponse response = new SuccessResponse(true, ACCEPT_DEAL, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(DEALS_REQUEST_COMPLETE_URL)
    public ResponseEntity<?> completeDeal(@PathVariable final Long dealId) {
        Long getterId = getLoggedInUserId();
        protectedDealService.completeDeal(dealId, getterId);
        SuccessResponse response = new SuccessResponse(true, COMPLETE_DEAL, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(DEALS_CANCEL_BEFORE_URL)
    public ResponseEntity<?> cancelBeforeDeal(@PathVariable final Long dealId) {
        Long getterId = getLoggedInUserId();
        protectedDealService.cancelBeforeDeal(dealId, getterId);
        SuccessResponse response = new SuccessResponse(true, CANCEL_BEFORE_WITHDRAW_DEAL, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(DEALS_CANCEL_AFTER)
    public ResponseEntity<?> cancelAfterDeal(@PathVariable final Long dealId){
        Long getterId = getLoggedInUserId();
        protectedDealService.cancelAfterDeal(dealId, getterId);
        SuccessResponse response = new SuccessResponse(true, CANCEL_AFTER_WITHDRAW_DEAL, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Long getLoggedInUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInformationResponse user = userService.findByEmail(email);
        return user.getId();
    }
}
