package com.api.v1.chat;

import com.core.domain.chat.dto.DirectMessageTotalRequest;
import com.core.domain.chat.dto.DirectMessageTotalResponse;
import com.core.domain.deal.dto.ProtectedDealResponse;
import com.core.domain.home.dto.HomeInformationResponse;
import com.core.domain.user.dto.UserProfileResponse;
import com.core.mapper.DirectMessageRoomMapper;
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
    private final DirectMessageRoomMapper directMessageRoomMapper;

    /**
     * 채팅방에 필요한 정보를 한번에 가져오는 요청
     */
    @PostMapping(CHAT_TOTAL_URL)
    public ResponseEntity<?> getDirectMessageTotalResponse(@RequestBody final DirectMessageTotalRequest request) {
        List<UserProfileResponse> senderReceiver = userService.findSenderReceiver(request.getSenderId(), request.getReceiverId());
        UserProfileResponse sender = senderReceiver.get(0);
        UserProfileResponse receiver = senderReceiver.get(1);
        HomeInformationResponse homeInfo = homeQueryService.findById((request.getHomeId()));

        Long providerId = Long.valueOf(homeInfo.getProviderId());
        Long getterId = providerId.equals(sender.getId()) ? receiver.getId() : sender.getId();

        boolean isAccountExist = userService.isExistAccount(sender.getId());
        List<ProtectedDealResponse> protectedDeals = protectedDealService.findProtectedDeal(getterId, providerId, request.getHomeId(), request.getRoomId());

        DirectMessageTotalResponse directMessageTotalResponse = directMessageRoomMapper.toDirectMessageTotalResponse(sender, receiver, homeInfo, isAccountExist, protectedDeals);
        SuccessResponse response = new SuccessResponse(true, FIND_CHATTING_ROOM, directMessageTotalResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
