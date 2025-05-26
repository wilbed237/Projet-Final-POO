package com.example.gestion_des_evenements.ServicesETGestionnaires;
import java.util.*;
import com.example.gestion_des_evenements.Interface.StrategieNotification;
import com.example.gestion_des_evenements.Participant.Participant;

public class NotificationSMS implements StrategieNotification {
    @Override
    public void notifier(List<Participant> participants, String message) {
        participants.forEach(p ->
                System.out.println("📱 SMS à " + p.getNom() + ": " + message));
    }
}
