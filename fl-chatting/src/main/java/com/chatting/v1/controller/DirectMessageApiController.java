package com.chatting.v1.controller;

import com.chatting.v1.constants.SuccessDirectMessages;
import com.chatting.v1.service.DirectMessageService;
import com.core.domain.chat.dto.*;
import com.infra.file.FileService;
import com.infra.utils.SuccessResponse;
import com.core.domain.chat.model.DirectMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.chatting.v1.constants.ApiUrlConstants.*;
import static com.chatting.v1.constants.SuccessDirectMessages.DIRECT_MESSAGE_UPLOAD_IMAGE_SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class DirectMessageApiController {

    private final SimpMessagingTemplate template;
    private final FileService fileHelper;
    private final DirectMessageService dmService;

    @MessageMapping(value = "/chat/message")
    public void sendMessage(final DirectMessageRequest directMessageRequest)  {
        DirectMessage directMessage = dmService.toDirectMessage(directMessageRequest);
        DirectMessageResponse directMessageResponse = dmService.toDirectMessageResponse(directMessage);
        dmService.saveDirectMessageAndPushFcm(directMessageRequest.getRoomId(), directMessage);
        template.convertAndSend("/sub/chat/room/" + directMessageRequest.getRoomId(), directMessageResponse);
    }

    @PostMapping(DM_SEND_IMAGE_URL)
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        String uploadUrl = fileHelper.toUrls(file);
        fileHelper.fileUpload(file, uploadUrl);
        return ResponseEntity.ok(new SuccessResponse(true, DIRECT_MESSAGE_UPLOAD_IMAGE_SUCCESS, uploadUrl));
    }

    @PostMapping(DM_SEND_FIRST_URL)
    public ResponseEntity<?> createDirectMessageRoom(@RequestBody final DirectMessageApplicationRequest dmDto) {
        Long roomId = dmService.createDirectMessageRoom(dmDto);
        SuccessResponse response = new SuccessResponse(true, SuccessDirectMessages.DIRECT_MESSAGE_ROOM_CREATE_SUCCESS, roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(DM_HISTORY_URL)
    public ResponseEntity<?> findDirectMessageHistory(@RequestParam final Long user1Id, @RequestParam final Long user2Id) {
        List<DirectMessageResponse> chatHistory = dmService.findChatHistory(user1Id, user2Id);
        SuccessResponse response = new SuccessResponse(true, SuccessDirectMessages.DIRECT_MESSAGE_HISTORY_FIND_SUCCESS, chatHistory);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(DM_CHECK_READ_URL)
    public ResponseEntity<?> checkReadMessage(@RequestBody final DirectMessageReadRequest directMessageReadRequest){
        dmService.checkReadMessages(directMessageReadRequest);
        SuccessResponse response = new SuccessResponse(true, SuccessDirectMessages.DIRECT_MESSAGE_ALL_READ, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(DM_FIND_ALL_ROOMS)
    public ResponseEntity<?> findDirectMessageList(@PathVariable final Long userId){
        List<DirectMessageRoomListResponse> directMessageRoomsByUser = dmService.getDirectMessageRoomsByUser(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessDirectMessages.DIRECT_MESSAGE_LIST_FIND_SUCCESS, directMessageRoomsByUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}