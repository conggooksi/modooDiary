package com.secondWind.modooDiary.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//@Configuration
//public class FcmConfig {
//    @Bean
//    public FirebaseMessaging firebaseMessaging() throws IOException {
//        ClassPathResource resource = new ClassPathResource("modoo-diary-35f14-firebase-adminsdk");
//
//        InputStream refreshToken = resource.getInputStream();
//
//        FirebaseApp firebaseApp = null;
//        List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
//
//        if (firebaseApps != null && !firebaseApps.isEmpty()) {
//            for (FirebaseApp app : firebaseApps) {
//                if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
//                    firebaseApp = app;
//                }
//            }
//        } else {
//            FirebaseOptions options = FirebaseOptions.builder()
//                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
//                    .build();
//
//            firebaseApp = FirebaseApp.initializeApp(options);
//        }
//
//        return FirebaseMessaging.getInstance(firebaseApp);
//    }
//}
