package com.infra.fcm;

import com.google.firebase.messaging.*;
import com.infra.exception.custom.FcmException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FCMNotificationService implements NotificationService {

    private final FirebaseMessaging firebaseMessaging;

    @Async
    @Override
    public void send(final NotificationState notificationState, final String token, final String title, final String content) {
        try {
            Message message = createMessage(notificationState, token, title, content);
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException ex) {
            throw new FcmException("fcm 전송 실패");
        }
    }


    private Message createMessage(final NotificationState fcmState, final String token, final String title, final String content) {
        return Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(content)
                        .build()
                )
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setAlert(ApsAlert.builder()
                                        .setTitle(title)
                                        .setBody(content)
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
                        .putCustomData("body", content)
                        .build()
                )
                .putData("title", title)
                .putData("body", content)
                .putData("isSave", fcmState.getValue())
                .build();
    }

}
