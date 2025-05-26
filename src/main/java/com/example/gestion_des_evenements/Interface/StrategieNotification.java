package com.example.gestion_des_evenements.Interface;

import com.example.gestion_des_evenements.Participant.Participant;

import java.util.List;

public interface StrategieNotification {
    void notifier(List<Participant> participants, String message);
}
