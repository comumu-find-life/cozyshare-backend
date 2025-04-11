package com.infra.fcm;


public interface NotificationService {
    void sendNotification(final NotificationState notificationState, final String token, String title, String content);
}
