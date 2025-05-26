package com.example.gestion_des_evenements.Evenement;
import com.example.gestion_des_evenements.Participant.Intervenant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

class Conference extends Evenement {
    private String theme;
    private List<Intervenant> intervenants;

    public Conference() {
        super();
        this.intervenants = new ArrayList<>();
    }

    public Conference(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String theme) {
        super(id, nom, date, lieu, capaciteMax);
        this.theme = theme;
        this.intervenants = new ArrayList<>();
    }

    @Override
    public void afficherDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("=== CONFÉRENCE ===");
        System.out.println("ID: " + id);
        System.out.println("Nom: " + nom);
        System.out.println("Thème: " + theme);
        System.out.println("Date: " + date.format(formatter));
        System.out.println("Lieu: " + lieu);
        System.out.println("Capacité: " + participants.size() + "/" + capaciteMax);
        System.out.println("Intervenants: " +
                intervenants.stream().map(Intervenant::getNom).collect(Collectors.joining(", ")));
    }

    // Getters et Setters
    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public List<Intervenant> getIntervenants() { return new ArrayList<>(intervenants); }
    public void setIntervenants(List<Intervenant> intervenants) { this.intervenants = intervenants; }

    public void ajouterIntervenant(Intervenant intervenant) {
        if (!intervenants.contains(intervenant)) {
            intervenants.add(intervenant);
        }
    }
}
