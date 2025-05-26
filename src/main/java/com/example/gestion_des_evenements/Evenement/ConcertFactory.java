package com.example.gestion_des_evenements.Evenement;

import java.time.LocalDateTime;
import java.util.Map;

class ConcertFactory extends EvenementFactory {
    @Override
    public Evenement creerEvenement(String id, String nom, LocalDateTime date,
                                    String lieu, int capaciteMax, Map<String, Object> parametres) {
        String artiste = (String) parametres.getOrDefault("artiste", "Artiste inconnu");
        String genre = (String) parametres.getOrDefault("genreMusical", "Non spécifié");
        return new Concert(id, nom, date, lieu, capaciteMax, artiste, genre);
    }
}
