package com.example.gestion_des_evenements.ServicesETGestionnaires;

import com.example.gestion_des_evenements.Evenement.Evenement;
import com.example.gestion_des_evenements.Evenement.Evenements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class GestionEvenements {
    private final Map<String, Evenements> evenements = new HashMap<>();
    private ObservableList<Evenements> evenementsObservable = FXCollections.observableArrayList();

    public boolean ajouterEvenement(Evenements evenement) {
        if (evenement == null || evenement.getId() == null || evenement.getId().trim().isEmpty()) {
            return false;
        }

        if (!evenements.containsKey(evenement.getId())) {
            evenements.put(evenement.getId(), evenement);
            evenementsObservable.add(evenement);
            return true;
        }
        return false;
    }

    public boolean supprimerEvenement(String id) {
        Evenements evenement = evenements.remove(id);
        if (evenement != null) {
            evenementsObservable.remove(evenement);
            return true;
        }
        return false;
    }

    public Evenements obtenirEvenement(String id) {
        return evenements.get(id);
    }

    public ObservableList<Evenements> getEvenementsObservable() {
        return evenementsObservable;
    }

    public boolean modifierEvenement(String ancienId, Evenement nouvelEvenement) {
        Evenements ancienEvenement = evenements.get(ancienId);
        if (ancienEvenement != null) {
            // Préserver le nombre de participants
            nouvelEvenement.setNombreParticipants(ancienEvenement.getNombreParticipants());

            // Si l'ID change, supprimer l'ancien et ajouter le nouveau
            if (!ancienId.equals(nouvelEvenement.getId())) {
                evenements.remove(ancienId);
                evenements.put(nouvelEvenement.getId(), nouvelEvenement);
            } else {
                evenements.put(ancienId, nouvelEvenement);
            }

            // Mettre à jour la liste observable
            int index = evenementsObservable.indexOf(ancienEvenement);
            if (index >= 0) {
                evenementsObservable.set(index, nouvelEvenement);
            }
            return true;
        }
        return false;
    }

    public boolean inscrireParticipant(String idEvenement) {
        Evenements evenement = evenements.get(idEvenement);
        if (evenement != null && evenement.getNombreParticipants() < evenement.getCapaciteMax()) {
            evenement.setNombreParticipants(evenement.getNombreParticipants() + 1);
            return true;
        }
        return false;
    }
}