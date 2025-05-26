package com.example.gestion_des_evenements.Participant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import com.example.gestion_des_evenements.Evenement.Evenement;

public class Organisateur extends Participant {
    private List<Evenement> evenementsOrganises;

    public Organisateur() {
        super();
        this.evenementsOrganises = new ArrayList<>();
    }

    public Organisateur(String id, String nom, String email) {
        super(id, nom, email);
        this.evenementsOrganises = new ArrayList<>();
    }

    public void ajouterEvenementOrganise(Evenement evenement) {
        if (!evenementsOrganises.contains(evenement)) {
            evenementsOrganises.add(evenement);
        }
    }

    public void retirerEvenementOrganise(Evenement evenement) {
        evenementsOrganises.remove(evenement);
    }

    public List<Evenement> getEvenementsOrganises() {
        return new ArrayList<>(evenementsOrganises);
    }

    public void setEvenementsOrganises(List<Evenement> evenementsOrganises) {
        this.evenementsOrganises = evenementsOrganises;
    }
}
