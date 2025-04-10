package com.infra.fcm;


public interface NotificationService {
    void send(final NotificationState notificationState, final String token, String title, String content);
}
