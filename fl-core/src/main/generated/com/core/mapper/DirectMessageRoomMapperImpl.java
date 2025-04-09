package com.core.mapper;

import com.core.domain.chat.dto.DirectMessageRoomInfoResponse;
import com.core.domain.chat.dto.DirectMessageRoomListResponse;
import com.core.domain.chat.dto.DirectMessageTotalResponse;
import com.core.domain.chat.model.DirectMessage;
import com.core.domain.chat.model.DirectMessageRoom;
import com.core.domain.deal.dto.ProtectedDealResponse;
import com.core.domain.home.dto.HomeInformationResponse;
import com.core.domain.user.dto.UserProfileResponse;
import com.core.domain.user.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-09T01:08:51+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class DirectMessageRoomMapperImpl implements DirectMessageRoomMapper {

    @Override
    public DirectMessageRoomInfoResponse toDirectMessageRoomInfoResponse(DirectMessageRoom directMessageRoom) {
        if ( directMessageRoom == null ) {
            return null;
        }

        DirectMessageRoomInfoResponse.DirectMessageRoomInfoResponseBuilder directMessageRoomInfoResponse = DirectMessageRoomInfoResponse.builder();

        directMessageRoomInfoResponse.id( directMessageRoom.getId() );
        directMessageRoomInfoResponse.senderId( directMessageRoom.getUser1().getId() );
        directMessageRoomInfoResponse.senderName( directMessageRoom.getUser1().getNickname() );
        directMessageRoomInfoResponse.receiverId( directMessageRoom.getUser2().getId() );
        directMessageRoomInfoResponse.receiverName( directMessageRoom.getUser2().getNickname() );

        return directMessageRoomInfoResponse.build();
    }

    @Override
    public DirectMessageRoomListResponse toDirectMessageRoomListResponse(DirectMessageRoom room, DirectMessage lastMessage, User otherUser, int notReadCount) {
        if ( room == null && lastMessage == null && otherUser == null ) {
            return null;
        }

        DirectMessageRoomListResponse.DirectMessageRoomListResponseBuilder directMessageRoomListResponse = DirectMessageRoomListResponse.builder();

        directMessageRoomListResponse.id( room.getId() );
        directMessageRoomListResponse.otherUserId( otherUser.getId() );
        directMessageRoomListResponse.userNickname( otherUser.getNickname() );
        directMessageRoomListResponse.userProfileUrl( otherUser.getProfileUrl() );
        directMessageRoomListResponse.progressHomeId( room.getProgressHomeId() );
        directMessageRoomListResponse.lastMessage( lastMessage.getMessage() );
        directMessageRoomListResponse.lastSendDateTime( lastMessage.getSentAt() );
        directMessageRoomListResponse.notReadCount( notReadCount );

        return directMessageRoomListResponse.build();
    }

    @Override
    public DirectMessageTotalResponse toDirectMessageTotalResponse(UserProfileResponse sender, UserProfileResponse receiver, HomeInformationResponse homeInformationResponse, boolean isAccountExist, List<ProtectedDealResponse> protectedDealResponses) {
        if ( sender == null && receiver == null && homeInformationResponse == null && protectedDealResponses == null ) {
            return null;
        }

        DirectMessageTotalResponse.DirectMessageTotalResponseBuilder directMessageTotalResponse = DirectMessageTotalResponse.builder();

        directMessageTotalResponse.sender( sender );
        directMessageTotalResponse.receiver( receiver );
        directMessageTotalResponse.homeInformationResponse( homeInformationResponse );
        directMessageTotalResponse.isExistAccount( isAccountExist );
        List<ProtectedDealResponse> list = protectedDealResponses;
        if ( list != null ) {
            directMessageTotalResponse.protectedDealResponse( new ArrayList<ProtectedDealResponse>( list ) );
        }

        return directMessageTotalResponse.build();
    }
}
