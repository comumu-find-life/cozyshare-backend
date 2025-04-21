package com.core.mapper;

import com.core.chat.dto.DirectMessageRequest;
import com.core.chat.dto.DirectMessageResponse;
import com.core.chat.model.DirectMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DirectMessageMapper {

    DirectMessageMapper INSTANCE = Mappers.getMapper(DirectMessageMapper.class);

    /**
     * DirectMessageRequest --> DirectMessage
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isRead", expression = "java(false)")
    @Mapping(target = "sentAt", expression = "java(java.time.LocalDateTime.now())")
    DirectMessage toDirectMessage(DirectMessageRequest request);

    /**
     * DirectMessage --> DirectMessageResponse
     */
    DirectMessageResponse toDirectMessageResponse(DirectMessage entity);


}
