package com.example.gestion_des_evenements.ServicesETGestionnaires;

import com.example.gestion_des_evenements.Interface.NotificationService;

class SMSNotificationService implements NotificationService {
    @Override
    public void envoyerNotification(String message) {
        System.out.println("📱 SMS envoyé: " + message);
    }
}
