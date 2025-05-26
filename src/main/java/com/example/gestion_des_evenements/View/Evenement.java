package com.example.gestion_des_evenements.View;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Evenement {
    private String id;
    private String nom;
    private String description;
    private LocalDateTime dateHeure;
    private String lieu;
    private int capaciteMax;
    private int nombreParticipants;

    public Evenement(String id, String nom, String description, LocalDateTime dateHeure, String lieu, int capaciteMax) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.dateHeure = dateHeure;
        this.lieu = lieu;
        this.capaciteMax = capaciteMax;
        this.nombreParticipants = 0;
    }

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDateHeure() { return dateHeure; }
    public void setDateHeure(LocalDateTime dateHeure) { this.dateHeure = dateHeure; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public int getCapaciteMax() { return capaciteMax; }
    public void setCapaciteMax(int capaciteMax) { this.capaciteMax = capaciteMax; }

    public int getNombreParticipants() { return nombreParticipants; }
    public void setNombreParticipants(int nombreParticipants) { this.nombreParticipants = nombreParticipants; }

    public String getDateHeureFormatee() {
        return dateHeure.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", nom, getDateHeureFormatee(), lieu);
    }
}