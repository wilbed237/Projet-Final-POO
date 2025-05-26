package com.example.gestion_des_evenements.Evenement;

import java.time.LocalDateTime;
import java.util.Map;

class ConferenceFactory extends EvenementFactory {
    @Override
    public Evenement creerEvenement(String id, String nom, LocalDateTime date,
                                    String lieu, int capaciteMax, Map<String, Object> parametres) {
        String theme = (String) parametres.getOrDefault("theme", "Thème général");
        return new Conference(id, nom, date, lieu, capaciteMax, theme);
    }
}
