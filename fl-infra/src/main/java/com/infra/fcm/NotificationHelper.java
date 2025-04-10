package com.infra.fcm;


public interface NotificationHelper {
    void send(final NotificationState notificationState, final String token, String title, String content);
}
