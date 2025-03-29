package com.core.mapper;

import com.core.domain.chat.dto.DirectMessageRoomInfoResponse;
import com.core.domain.chat.dto.DirectMessageRoomListResponse;
import com.core.domain.chat.dto.DirectMessageTotalResponse;
import com.core.domain.chat.model.DirectMessageRoom;
import com.core.domain.deal.dto.ProtectedDealResponse;
import com.core.domain.home.dto.HomeInformationResponse;
import com.core.domain.user.dto.UserProfileResponse;
import com.core.domain.user.model.User;
import com.core.domain.chat.model.DirectMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DirectMessageRoomMapper {

    DirectMessageRoomMapper INSTANCE = Mappers.getMapper(DirectMessageRoomMapper.class);

    @Mapping(target = "id", expression = "java(directMessageRoom.getId())")
    @Mapping(target = "senderId", expression = "java(directMessageRoom.getUser1().getId())")
    @Mapping(target = "senderName", expression = "java(directMessageRoom.getUser1().getNickname())")
    @Mapping(target = "receiverId", expression = "java(directMessageRoom.getUser2().getId())")
    @Mapping(target = "receiverName", expression = "java(directMessageRoom.getUser2().getNickname())")
    DirectMessageRoomInfoResponse toDirectMessageRoomInfoResponse(DirectMessageRoom directMessageRoom);


    @Mapping(target = "id", expression = "java(room.getId())")
    @Mapping(target = "otherUserId" ,expression = "java(otherUser.getId())")
    @Mapping(target = "userNickname",expression = "java(otherUser.getNickname())")
    @Mapping(target = "userProfileUrl",expression ="java(otherUser.getProfileUrl())")
    @Mapping(target = "progressHomeId",expression = "java(room.getProgressHomeId())")
    @Mapping(target = "lastMessage" ,expression = "java(lastMessage.getMessage())")
    @Mapping(target = "lastSendDateTime",expression = "java(lastMessage.getSentAt())")
    @Mapping(target = "notReadCount", expression = "java(notReadCount)")
    DirectMessageRoomListResponse toDirectMessageRoomListResponse(DirectMessageRoom room, DirectMessage lastMessage, User otherUser, int notReadCount);


    @Mapping(target = "sender", source = "sender")
    @Mapping(target = "receiver", source = "receiver")
    @Mapping(target = "homeInformationResponse", source = "homeInformationResponse")
    @Mapping(target = "isExistAccount", source = "isAccountExist")
    @Mapping(target = "protectedDealResponse", source = "protectedDealResponses")
    DirectMessageTotalResponse toDirectMessageTotalResponse(UserProfileResponse sender, UserProfileResponse receiver, HomeInformationResponse homeInformationResponse, boolean isAccountExist, List<ProtectedDealResponse> protectedDealResponses);

}
