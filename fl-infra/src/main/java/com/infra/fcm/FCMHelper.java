package com.infra.fcm;

import com.google.firebase.messaging.*;
import com.infra.exception.custom.FcmException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FCMHelper {

    private final FirebaseMessaging firebaseMessaging;

    @Async
    public void sendNotification(final FCMState fcmState, final String token, final String title, final String body) {
        try {
            Message message = createMessage(fcmState, token, title, body);
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException ex) {
            throw new FcmException("fcm 전송 실패");
        }
    }

    private Message createMessage(final FCMState fcmState, final String token, final String title, final String body) {
        return Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build()
                )
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setAlert(ApsAlert.builder()
                                        .setTitle(title)
                                        .setBody(body)
                                        .build()
                                )
                                .setContentAvailable(true)
                                .setMutableContent(true)
                                .build()
                        )
                        .putHeader("apns-push-type", "alert")
                        .putHeader("apns-priority", "10")
                        .putCustomData("isSave", fcmState.getValue())
                        .putCustomData("title", title)
                        .putCustomData("body", body)
                        .build()
                )
                .putData("title", title)
                .putData("body", body)
                .putData("isSave", fcmState.getValue())
                .build();
    }
}
