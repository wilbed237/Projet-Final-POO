package com.example.gestion_des_evenements.Evenement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


import javafx.beans.property.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.gestion_des_evenements.Exception.CapaciteMaxAtteinteException;
import com.example.gestion_des_evenements.Interface.EvenementObservable;
import com.example.gestion_des_evenements.Interface.ParticipantObserver;
import com.example.gestion_des_evenements.Participant.Participant;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

// Classe abstraite Evenement
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Conference.class, name = "conference"),
        @JsonSubTypes.Type(value = Concert.class, name = "concert")
})
public abstract class Evenement extends Evenements implements EvenementObservable {
    protected String id;
    protected String nom;
    protected LocalDateTime date;
    protected String lieu;
    protected int capaciteMax;
    protected List<Participant> participants;
    protected List<ParticipantObserver> observers;

    public Evenement() {
        this.participants = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public Evenement(String id, String nom, LocalDateTime date, String lieu, int capaciteMax) {
        this();
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.capaciteMax = capaciteMax;
    }

    public Evenement(String id, String nom, String description, LocalDateTime dateHeure, String lieu, int capacite) {

    }

    public void ajouterParticipant(Participant participant) throws CapaciteMaxAtteinteException {
        if (participants.size() >= capaciteMax) {
            throw new CapaciteMaxAtteinteException("Capacité maximale atteinte pour l'événement " + nom);
        }

        if (!participants.contains(participant)) {
            participants.add(participant);
            // Ajouter comme observer si c'est un ParticipantObserver
            if (participant instanceof ParticipantObserver) {
                ajouterObserver((ParticipantObserver) participant);
            }
            System.out.println("Participant " + participant.getNom() + " ajouté à " + nom);
        }
    }

    public void retirerParticipant(Participant participant) {
        if (participants.remove(participant)) {
            if (participant instanceof ParticipantObserver) {
                supprimerObserver((ParticipantObserver) participant);
            }
            System.out.println("Participant " + participant.getNom() + " retiré de " + nom);
        }
    }

    public void annuler() {
        notifierObservers("L'événement '" + nom + "' a été annulé.");
    }

    public abstract void afficherDetails();

    // Observer Pattern Implementation
    @Override
    public void ajouterObserver(ParticipantObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void supprimerObserver(ParticipantObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifierObservers(String message) {
        observers.forEach(observer -> observer.notifier(message));
    }

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public int getCapaciteMax() { return capaciteMax; }
    public void setCapaciteMax(int capaciteMax) { this.capaciteMax = capaciteMax; }

    public List<Participant> getParticipants() { return new ArrayList<>(participants); }
    public void setParticipants(List<Participant> participants) { this.participants = participants; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Evenement evenement = (Evenement) obj;
        return Objects.equals(id, evenement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setNombreParticipants(int nombreParticipants) {

    }
}


