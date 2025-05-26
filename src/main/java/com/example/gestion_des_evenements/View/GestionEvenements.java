package com.example.gestion_des_evenements.View;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class GestionEvenements {
    private Map<String, Evenement> evenements = new HashMap<>();

    public boolean ajouterEvenement(Evenement evenement) {
        if (evenement == null || evenement.getId() == null) {
            return false;
        }

        if (!evenements.containsKey(evenement.getId())) {
            evenements.put(evenement.getId(), evenement);
            return true;
        }
        return false;
    }

    public boolean supprimerEvenement(String id) {
        return evenements.remove(id) != null;
    }

    public Evenement obtenirEvenement(String id) {
        return evenements.get(id);
    }

    public List<Evenement> obtenirTousLesEvenements() {
        return new ArrayList<>(evenements.values());
    }

    public boolean modifierEvenement(String id, Evenement nouvelEvenement) {
        if (evenements.containsKey(id)) {
            nouvelEvenement.setId(id);
            evenements.put(id, nouvelEvenement);
            return true;
        }
        return false;
    }

    public boolean inscrireParticipant(String idEvenement) {
        Evenement evenement = evenements.get(idEvenement);
        if (evenement != null && evenement.getNombreParticipants() < evenement.getCapaciteMax()) {
            evenement.setNombreParticipants(evenement.getNombreParticipants() + 1);
            return true;
        }
        return false;
    }
}
