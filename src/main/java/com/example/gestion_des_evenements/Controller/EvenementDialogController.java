package com.example.gestion_des_evenements.Controller;

import com.example.gestion_des_evenements.Evenement.Evenement;
import com.example.gestion_des_evenements.Evenement.Evenements;

import com.example.gestion_des_evenements.ServicesETGestionnaires.GestionEvenements;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class EvenementDialogController implements Initializable {

    @FXML private TextField txtId;
    @FXML private TextField txtNom;
    @FXML private TextArea txtDescription;
    @FXML private TextField txtDateHeure;
    @FXML private TextField txtLieu;
    @FXML private TextField txtCapacite;
    @FXML private Button btnOK;
    @FXML private Button btnAnnuler;

    private GestionEvenements gestionEvenements;
    private Evenements evenementAModifier;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtDateHeure.setPromptText("jj/MM/aaaa HH:mm");
        txtDescription.setPrefRowCount(3);
    }

    public void setGestionEvenements(GestionEvenements gestionEvenements) {
        this.gestionEvenements = gestionEvenements;
    }

    public void setEvenementAModifier(Evenements evenement) {
        this.evenementAModifier = evenement;
        if (evenement != null) {
            txtId.setText(evenement.getId());
            txtId.setDisable(true); // ID non modifiable en modification
            txtNom.setText(evenement.getNom());
            txtDescription.setText(evenement.getDescription());
            txtDateHeure.setText(evenement.getDateHeureFormatee());
            txtLieu.setText(evenement.getLieu());
            txtCapacite.setText(String.valueOf(evenement.getCapaciteMax()));
        }
    }

    @FXML
    private void handleOK() {
        if (validerChamps()) {
            try {
                Evenement evenement = creerEvenementDepuisFormulaire();

                boolean succes;
                if (evenementAModifier == null) {
                    // Ajout
                    succes = gestionEvenements.ajouterEvenement(evenement);
                    if (!succes) {
                        afficherErreur("Un événement avec cet ID existe déjà!");
                        return;
                    }
                } else {
                    // Modification
                    succes = gestionEvenements.modifierEvenement(evenementAModifier.getId(), evenement);
                    if (!succes) {
                        afficherErreur("Erreur lors de la modification!");
                        return;
                    }
                }

                fermerDialogue();

            } catch (DateTimeParseException e) {
                afficherErreur("Format de date invalide! Utilisez: jj/MM/aaaa HH:mm");
            } catch (NumberFormatException e) {
                afficherErreur("La capacité doit être un nombre valide!");
            } catch (Exception e) {
                afficherErreur("Erreur: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleAnnuler() {
        fermerDialogue();
    }

    private boolean validerChamps() {
        StringBuilder erreurs = new StringBuilder();

        if (txtId.getText().trim().isEmpty()) {
            erreurs.append("- L'ID est obligatoire\n");
        }
        if (txtNom.getText().trim().isEmpty()) {
            erreurs.append("- Le nom est obligatoire\n");
        }
        if (txtLieu.getText().trim().isEmpty()) {
            erreurs.append("- Le lieu est obligatoire\n");
        }
        if (txtDateHeure.getText().trim().isEmpty()) {
            erreurs.append("- La date/heure est obligatoire\n");
        }
        if (txtCapacite.getText().trim().isEmpty()) {
            erreurs.append("- La capacité est obligatoire\n");
        }

        if (erreurs.length() > 0) {
            afficherErreur("Veuillez corriger les erreurs suivantes:\n\n" + erreurs.toString());
            return false;
        }

        return true;
    }

    private Evenement creerEvenementDepuisFormulaire() throws DateTimeParseException, NumberFormatException {
        String id = txtId.getText().trim();
        String nom = txtNom.getText().trim();
        String description = txtDescription.getText().trim();
        String lieu = txtLieu.getText().trim();

        LocalDateTime dateHeure = LocalDateTime.parse(txtDateHeure.getText().trim(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        int capacite = Integer.parseInt(txtCapacite.getText().trim());

        if (capacite <= 0) {
            throw new NumberFormatException("La capacité doit être positive");
        }

        return new Evenement(id, nom, description, dateHeure, lieu, capacite) {
            @Override
            public void afficherDetails() {

            }
        };
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void fermerDialogue() {
        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.close();
    }
}
