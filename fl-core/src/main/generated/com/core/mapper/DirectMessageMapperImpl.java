package com.core.mapper;

import com.core.chat.dto.DirectMessageRequest;
import com.core.chat.dto.DirectMessageResponse;
import com.core.chat.model.DirectMessage;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-25T17:42:58+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class DirectMessageMapperImpl implements DirectMessageMapper {

    @Override
    public DirectMessage toDirectMessage(DirectMessageRequest request) {
        if ( request == null ) {
            return null;
        }

        DirectMessage.DirectMessageBuilder directMessage = DirectMessage.builder();

        directMessage.senderId( request.getSenderId() );
        directMessage.receiverId( request.getReceiverId() );
        directMessage.message( request.getMessage() );
        directMessage.dealId( request.getDealId() );
        directMessage.imageUrl( request.getImageUrl() );
        directMessage.isDeal( request.getIsDeal() );
        directMessage.dealState( request.getDealState() );

        directMessage.isRead( false );
        directMessage.sentAt( java.time.LocalDateTime.now() );

        return directMessage.build();
    }

    @Override
    public DirectMessageResponse toDirectMessageResponse(DirectMessage entity) {
        if ( entity == null ) {
            return null;
        }

        DirectMessageResponse.DirectMessageResponseBuilder directMessageResponse = DirectMessageResponse.builder();

        directMessageResponse.senderId( entity.getSenderId() );
        directMessageResponse.receiverId( entity.getReceiverId() );
        directMessageResponse.dealId( entity.getDealId() );
        directMessageResponse.message( entity.getMessage() );
        directMessageResponse.isDeal( entity.getIsDeal() );
        directMessageResponse.dealState( entity.getDealState() );
        directMessageResponse.sentAt( entity.getSentAt() );

        return directMessageResponse.build();
    }
}
