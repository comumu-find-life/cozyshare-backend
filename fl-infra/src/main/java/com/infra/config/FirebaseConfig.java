package com.infra.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.*;


@Configuration
public class FirebaseConfig {

    @Value(("${firebase.project-id}"))
    private String projectId;

    @Value(("${firebase.key-url}"))
    private String keyPath;

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        try (FileInputStream serviceAccount = new FileInputStream(keyPath)) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(projectId)
                    .build();

            FirebaseApp app;
            if (FirebaseApp.getApps().isEmpty()) {
                app = FirebaseApp.initializeApp(options);
            } else {
                app = FirebaseApp.getInstance();
            }

            return FirebaseMessaging.getInstance(app);
        }
    }

//    @Bean
//    FirebaseMessaging firebaseMessaging() throws IOException {
//        ClassPathResource resource = new ClassPathResource(keyName);
//        InputStream refreshToken = resource.getInputStream();
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(refreshToken))
//                .setProjectId(projectId)
//                .build();
//        FirebaseApp app;
//        if (FirebaseApp.getApps().isEmpty()) {
//            app = FirebaseApp.initializeApp(options);
//        } else {
//            app = FirebaseApp.getInstance();
//        }
//        return FirebaseMessaging.getInstance(app);
//    }
}