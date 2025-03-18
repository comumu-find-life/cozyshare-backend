package com.api.v1.chat;

import com.core.domain.chat.dto.DirectMessageTotalRequest;
import com.core.domain.chat.dto.DirectMessageTotalResponse;
import com.core.domain.deal.dto.ProtectedDealResponse;
import com.core.domain.home.dto.HomeInformationResponse;
import com.core.domain.user.dto.UserProfileResponse;
import com.infra.utils.SuccessResponse;
import com.core.domain.deal.service.ProtectedDealService;
import com.core.domain.home.service.HomeQueryService;
import com.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.api.v1.constants.ApiUrlConstants.CHAT_TOTAL_URL;
import static com.api.v1.constants.ResponseMessage.FIND_CHATTING_ROOM;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ChatController {

    private final UserService userService;
    private final HomeQueryService homeQueryService;
    private final ProtectedDealService protectedDealService;

    /**
     * 채팅방에 필요한 정보를 한번에 가져오는 요청
     */
    @PostMapping(CHAT_TOTAL_URL)
    public ResponseEntity<?> getDirectMessageTotalResponse(@RequestBody final DirectMessageTotalRequest request) {
        UserProfileResponse sender = getSender(request);
        UserProfileResponse receiver = getReceiver(request);
        HomeInformationResponse homeInfo = getHomeInformation(request.getHomeId());

        Long[] roles = determineRoles(homeInfo, sender, receiver);
        Long getterId = roles[0];
        Long providerId = roles[1];

        boolean isAccountExist = userService.isExistAccount(sender.getId());
        List<ProtectedDealResponse> protectedDeals = getProtectedDeals(request, getterId, providerId);

        DirectMessageTotalResponse response = buildDirectMessageTotalResponse(sender, receiver, homeInfo, isAccountExist, protectedDeals);
        return buildSuccessResponse(response, FIND_CHATTING_ROOM);
    }

    private UserProfileResponse getSender(DirectMessageTotalRequest request) {
        return userService.findSenderReceiver(request.getSenderId(), request.getReceiverId()).get(0);
    }

    private UserProfileResponse getReceiver(DirectMessageTotalRequest request) {
        return userService.findSenderReceiver(request.getSenderId(), request.getReceiverId()).get(1);
    }

    private HomeInformationResponse getHomeInformation(Long homeId) {
        return homeQueryService.findById(homeId);
    }

    private Long[] determineRoles(HomeInformationResponse homeInfo, UserProfileResponse sender, UserProfileResponse receiver) {
        Long providerId = Long.valueOf(homeInfo.getProviderId());
        if (providerId.equals(sender.getId())) {
            return new Long[]{receiver.getId(), sender.getId()};
        } else {
            return new Long[]{sender.getId(), receiver.getId()};
        }
    }

    private List<ProtectedDealResponse> getProtectedDeals(DirectMessageTotalRequest request, Long getterId, Long providerId) {
        return protectedDealService.findProtectedDeal(getterId, providerId, request.getHomeId(), request.getRoomId());
    }

    private DirectMessageTotalResponse buildDirectMessageTotalResponse(UserProfileResponse sender, UserProfileResponse receiver,
                                                                       HomeInformationResponse homeInfo, boolean isAccountExist,
                                                                       List<ProtectedDealResponse> protectedDeals) {
        return DirectMessageTotalResponse.builder()
                .sender(sender)
                .receiver(receiver)
                .homeInformationResponse(homeInfo)
                .isExistAccount(isAccountExist)
                .protectedDealResponse(protectedDeals)
                .build();
    }

    private ResponseEntity<SuccessResponse> buildSuccessResponse(DirectMessageTotalResponse data, String message) {
        SuccessResponse response = new SuccessResponse(true, message, data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
