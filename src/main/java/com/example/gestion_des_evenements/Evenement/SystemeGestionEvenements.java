package com.example.gestion_des_evenements.Evenement;

import com.example.gestion_des_evenements.Exception.EvenementDejaExistantException;
import com.example.gestion_des_evenements.Participant.Organisateur;
import com.example.gestion_des_evenements.Participant.Participant;
import com.example.gestion_des_evenements.ServicesETGestionnaires.GestionnaireNotification;
import com.example.gestion_des_evenements.ServicesETGestionnaires.NotificationEmail;
import com.example.gestion_des_evenements.ServicesETGestionnaires.NotificationSMS;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SystemeGestionEvenements {
    public static void main(String[] args) {
        try {
            // Test du système complet
            demonstrationComplete();
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static void demonstrationComplete() throws Exception {
        System.out.println("=== DÉMONSTRATION DU SYSTÈME DE GESTION D'ÉVÉNEMENTS ===\n");

        // 1. Récupération du gestionnaire (Singleton)
        GestionEvenements gestionnaire = GestionEvenements.getInstance();

        // 2. Création d'événements avec Factory Pattern
        Map<String, Object> paramsConf = new HashMap<>();
        paramsConf.put("theme", "Intelligence Artificielle");

        Evenement conference = EvenementFactoryProvider
                .getFactory("conference")
                .creerEvenement("CONF001", "IA Summit 2025",
                        LocalDateTime.of(2025, 6, 15, 9, 0),
                        "Centre de Congrès", 100, paramsConf);

        Map<String, Object> paramsConcert = new HashMap<>();
        paramsConcert.put("artiste", "Les Développeurs Harmoniques");
        paramsConcert.put("genreMusical", "Code Rock");

        Evenement concert = EvenementFactoryProvider
                .getFactory("concert")
                .creerEvenement("CONC001", "Concert Tech",
                        LocalDateTime.of(2025, 7, 20, 20, 0),
                        "Salle de Spectacle", 200, paramsConcert);

        // 3. Ajout des événements
        gestionnaire.ajouterEvenement(conference);
        gestionnaire.ajouterEvenement(concert);

        // 4. Création de participants
        Participant p1 = new Participant("P001", "Alice Dupont", "alice@email.com");
        Participant p2 = new Participant("P002", "Bob Martin", "bob@email.com");
        Organisateur org1 = new Organisateur("ORG001", "Claire Admin", "claire@email.com");

        // 5. Inscription aux événements (Observer Pattern en action)
        conference.ajouterParticipant(p1);
        conference.ajouterParticipant(p2);
        conference.ajouterParticipant(org1);

        concert.ajouterParticipant(p1);

        // 6. Affichage des détails
        conference.afficherDetails();
        System.out.println();
        concert.afficherDetails();
        System.out.println();

        // 7. Test Strategy Pattern pour notifications
        GestionnaireNotification gestNotif = new GestionnaireNotification(new NotificationEmail());
        gestNotif.envoyerNotification(Arrays.asList(p1, p2), "Rappel: Conférence demain");

        gestNotif.setStrategie(new NotificationSMS());
        gestNotif.envoyerNotification(Arrays.asList(p1), "Concert ce soir!");

        // 8. Test des exceptions
        try {
            gestionnaire.ajouterEvenement(conference); // Doit lever une exception
        } catch (EvenementDejaExistantException e) {
            System.out.println("Exception capturée: " + e.getMessage());
        }

        // 9. Test notification asynchrone
        System.out.println("\n=== Test notification asynchrone ===");
        CompletableFuture<Void> futureNotif = gestionnaire
                .envoyerNotificationAsynchrone("Nouvelle fonctionnalité disponible!");

        System.out.println("Notification envoyée en arrière-plan...");
        futureNotif.join(); // Attendre la fin

        // 10. Test Observer Pattern - Annulation d'événement
        System.out.println("\n=== Test annulation (Observer Pattern) ===");
        conference.annuler();

        // 11. Test des streams et lambdas
        System.out.println("\n=== Recherche avec Streams ===");
        List<Evenement> evenementsIA = gestionnaire.rechercherParNom("IA");
        evenementsIA.forEach(e -> System.out.println("Trouvé: " + e.getNom()));

        System.out.println("\n=== Système testé avec succès! ===");
    }
}