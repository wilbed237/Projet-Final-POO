package com.example.gestion_des_evenements.ServicesETGestionnaires;

import com.example.gestion_des_evenements.Interface.StrategieNotification;
import com.example.gestion_des_evenements.Participant.Participant;

import java.util.List;

public class GestionnaireNotification {
    private StrategieNotification strategie;

    public GestionnaireNotification(StrategieNotification strategie) {
        this.strategie = strategie;
    }

    public void setStrategie(StrategieNotification strategie) {
        this.strategie = strategie;
    }

    public void envoyerNotification(List<Participant> participants, String message) {
        strategie.notifier(participants, message);
    }
}
