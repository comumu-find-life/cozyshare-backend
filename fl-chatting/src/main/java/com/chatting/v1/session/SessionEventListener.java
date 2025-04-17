package com.chatting.v1.session;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class SessionEventListener {

    private final WebSocketSessionManager sessionManager;

    @EventListener
    public void handleSessionConnected(final SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Long roomId = Long.valueOf(headerAccessor.getNativeHeader("roomId").get(0));
        Long userId = Long.valueOf(headerAccessor.getNativeHeader("userId").get(0));
        sessionManager.addSession(headerAccessor.getSessionId(), roomId, userId);
    }

    @EventListener
    public void handleSessionDisconnect(final SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        sessionManager.removeSession(sessionId);
    }
}