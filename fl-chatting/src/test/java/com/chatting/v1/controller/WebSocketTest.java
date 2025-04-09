package com.chatting.v1.controller;

import com.core.domain.chat.dto.DirectMessageRequest;
import com.core.domain.chat.repository.DirectMessageRepository;
import com.core.domain.user.model.User;
import com.core.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.*;

import static com.core.chat.dto_helper.DirectMessageDtoHelper.generateDirectMessageRequest;
import static com.core.user.entity_helper.UserHelper.generateUser;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class WebSocketTest {

    @Value("${domain.chat.directMessage}")
    private String dmUrl;

    @Value("${domain.chat.subscribe}")
    private String sub;

    @Value("${domain.chat.publish}")
    private String pub;

    @LocalServerPort
    private int port;

    private WebSocketStompClient stompClient;
    private static final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private final BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DirectMessageRepository directMessageRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setup() {
        mongoTemplate.getDb().drop();
        List<Transport> transports = List.of(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
        stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    public void testChatMessageSendAndReceive() throws Exception {
        // given
        User sender = userRepository.save(generateUser(1L));
        User receiver = userRepository.save(generateUser(2L));
        Long roomId = 1L;

        DirectMessageRequest message = generateDirectMessageRequest(
                sender.getId(), receiver.getId(), roomId, "message");

        StompHeaders connectHeaders = new StompHeaders();
        connectHeaders.add("roomId", String.valueOf(roomId));
        connectHeaders.add("userId", String.valueOf(sender.getId()));

        CountDownLatch latch = new CountDownLatch(1);

        StompSession session = stompClient.connectAsync(
                        "ws://localhost:" + port + dmUrl,
                        headers,
                        connectHeaders,
                        new StompSessionHandlerAdapter() {})
                .get(2, TimeUnit.SECONDS);

        // 구독
        session.subscribe(sub + "/chat/room/" + roomId, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try {
                    String messagePayload = objectMapper.writeValueAsString(payload);
                    blockingQueue.offer(messagePayload);
                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // 구독 완료까지 약간 대기
        Thread.sleep(500); // 또는 latch.await(1, TimeUnit.SECONDS);

        // 메시지 전송
        session.send(pub + "/chat/message", message);

        // 결과 받기
        latch.await(10, TimeUnit.SECONDS);
        String received = blockingQueue.poll(1, TimeUnit.SECONDS);

        assertThat(received).isNotNull();
        System.out.println("📨 Received message: " + received);
    }
}