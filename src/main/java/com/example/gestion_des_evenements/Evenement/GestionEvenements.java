package com.example.gestion_des_evenements.Evenement;
import com.example.gestion_des_evenements.Exception.EvenementDejaExistantException;
import com.example.gestion_des_evenements.Exception.EvenementInexistantException;
import com.example.gestion_des_evenements.Interface.NotificationService;
import com.example.gestion_des_evenements.ServicesETGestionnaires.EmailNotificationService;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class GestionEvenements {
    private static GestionEvenements instance;
    private Map<String, Evenement> evenements = new HashMap<>();
    private NotificationService notificationService;

    private void GestionEvenements() {
        this.evenements = new HashMap<>();
        this.notificationService = new EmailNotificationService(); // Par défaut

    }
    public GestionEvenements() {
        this.notificationService = new EmailNotificationService();
    }

    public static synchronized GestionEvenements getInstance() {
        if (instance == null) {
            instance = new GestionEvenements();
        }
        return instance;
    }

    public void ajouterEvenement(Evenement evenement) throws EvenementDejaExistantException {
        if (evenements.containsKey(evenement.getId())) {
            throw new EvenementDejaExistantException("Événement avec l'ID " + evenement.getId() + " existe déjà");
        }
        evenements.put(evenement.getId(), evenement);
        System.out.println("Événement '" + evenement.getNom() + "' ajouté avec succès");
    }

    public void supprimerEvenement(String id) throws EvenementInexistantException {
        Evenement evenement = evenements.remove(id);
        if (evenement == null) {
            throw new EvenementInexistantException("Événement avec l'ID " + id + " n'existe pas");
        }
        evenement.annuler(); // Notifie les participants
        System.out.println("Événement supprimé: " + evenement.getNom());
    }

    public Evenement rechercherEvenement(String id) throws EvenementInexistantException {
        Evenement evenement = evenements.get(id);
        if (evenement == null) {
            throw new EvenementInexistantException("Événement avec l'ID " + id + " n'existe pas");
        }
        return evenement;
    }

    public List<Evenement> getTousLesEvenements() {
        return new ArrayList<>(evenements.values());
    }

    public List<Evenement> rechercherParNom(String nom) {
        return evenements.values().stream()
                .filter(e -> e.getNom().toLowerCase().contains(nom.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Evenement> rechercherParLieu(String lieu) {
        return evenements.values().stream()
                .filter(e -> e.getLieu().toLowerCase().contains(lieu.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void setNotificationService(NotificationService service) {
        this.notificationService = service;
    }

    // Méthode pour notification asynchrone
    public CompletableFuture<Void> envoyerNotificationAsynchrone(String message) {
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000); // Simule un délai d'envoi
                notificationService.envoyerNotification(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Notification interrompue: " + e.getMessage());
            }
        });
    }

    public Map<String, Evenement> getEvenements() {
        return new HashMap<>(evenements);
    }

    public void setEvenements(Map<String, Evenement> evenements) {
        this.evenements = evenements;
    }

}

