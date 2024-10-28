package com.wipro.controller;

import com.wipro.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    // Injeção do NotificationService no construtor

    /*public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }*/

    @GetMapping("/sendNotification")
    public String sendNotification() {
        notificationService.sendNotification("Hello from Spring!");
        return "Notification sent!";
    }
}
