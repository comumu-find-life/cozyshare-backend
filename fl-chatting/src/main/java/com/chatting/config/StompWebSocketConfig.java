package com.chatting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@EnableWebSocketMessageBroker
@Configuration
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {


    // /dm
    @Value("${domain.chat.directMessage}")
    private String dmUrl;

    // /pub
    @Value("${domain.chat.publish}")
    private String pub;

    //sub
    @Value("${domain.chat.subscribe}")
    private String sub;


    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint(dmUrl)
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes(pub);
        registry.enableSimpleBroker(sub);
    }
}

