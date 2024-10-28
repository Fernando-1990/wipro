package com.wipro.service;

import org.springframework.stereotype.Component;


@Component
public class NotificationService {
    public void sendNotification(String message) {
        System.out.println("Sending notification: " + message);
    }
}
