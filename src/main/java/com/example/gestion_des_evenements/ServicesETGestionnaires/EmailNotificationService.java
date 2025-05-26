package com.example.gestion_des_evenements.ServicesETGestionnaires;

import com.example.gestion_des_evenements.Interface.NotificationService;

public class EmailNotificationService implements NotificationService {
    @Override
    public void envoyerNotification(String message) {
        System.out.println("📧 Email envoyé: " + message);
    }
}
