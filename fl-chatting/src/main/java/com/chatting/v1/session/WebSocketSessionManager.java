package com.chatting.v1.session;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

    private final Map<String, Long> sessionRoomMap = new ConcurrentHashMap<>();
    private final Map<String, Long> sessionUserMap = new ConcurrentHashMap<>();

    public void addSession(String sessionId, Long roomId, Long userId) {
        sessionRoomMap.put(sessionId, roomId);
        sessionUserMap.put(sessionId, userId);
    }

    public void removeSession(String sessionId) {
        sessionRoomMap.remove(sessionId);
        sessionUserMap.remove(sessionId);
    }

    public boolean isUserInRoom(Long roomId, Long userId) {
        return sessionRoomMap.entrySet().stream()
                .anyMatch(entry -> entry.getValue().equals(roomId) &&
                        userId.equals(sessionUserMap.get(entry.getKey())));
    }
}