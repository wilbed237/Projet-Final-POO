package com.example.gestion_des_evenements.Controller;


import com.example.gestion_des_evenements.Evenement.Evenements;
import com.example.gestion_des_evenements.ServicesETGestionnaires.GestionEvenements;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private TableView<Evenements> tableEvenements;
    @FXML private TableColumn<Evenements, String> colId;
    @FXML private TableColumn<Evenements, String> colNom;
    @FXML private TableColumn<Evenements, String> colDescription;
    @FXML private TableColumn<Evenements, String> colDate;
    @FXML private TableColumn<Evenements, String> colLieu;
    @FXML private TableColumn<Evenements, Integer> colCapacite;
    @FXML private TableColumn<Evenements, Integer> colParticipants;

    @FXML private Button btnAjouter;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;
    @FXML private Button btnInscrire;
    @FXML private Button btnActualiser;

    private GestionEvenements gestionEvenements = new GestionEvenements();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurerTableau();
        chargerDonneesExemple();

        // Lier la table aux données observables
        tableEvenements.setItems(gestionEvenements.getEvenementsObservable());
    }

    private void configurerTableau() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDateHeureFormatee()));
        colLieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        colCapacite.setCellValueFactory(new PropertyValueFactory<>("capaciteMax"));
        colParticipants.setCellValueFactory(new PropertyValueFactory<>("nombreParticipants"));
    }

    @FXML
    private void handleAjouterEvenement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EvenementDialog.fxml"));
            Parent root = loader.load();

            EvenementDialogController controller = loader.getController();


            Stage stage = new Stage();
            stage.setTitle("Ajouter un Événement");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            afficherErreur("Erreur lors de l'ouverture du dialogue", e.getMessage());
        }
    }

    @FXML
    private void handleModifierEvenement() {
        Evenements evenementSelectionne = tableEvenements.getSelectionModel().getSelectedItem();
        if (evenementSelectionne == null) {
            afficherAvertissement("Aucune sélection", "Veuillez sélectionner un événement à modifier.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EvenementDialog.fxml"));
            Parent root = loader.load();

            EvenementDialogController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Modifier l'Événement");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            afficherErreur("Erreur lors de l'ouverture du dialogue", e.getMessage());
        }
    }

    @FXML
    private void handleSupprimerEvenement() {
        Evenements evenementSelectionne = tableEvenements.getSelectionModel().getSelectedItem();
        if (evenementSelectionne == null) {
            afficherAvertissement("Aucune sélection", "Veuillez sélectionner un événement à supprimer.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText("Supprimer l'événement");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer l'événement \"" +
                evenementSelectionne.getNom() + "\" ?");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (gestionEvenements.supprimerEvenement(evenementSelectionne.getId())) {
                afficherInformation("Succès", "Événement supprimé avec succès!");
            } else {
                afficherErreur("Erreur", "Impossible de supprimer l'événement.");
            }
        }
    }

    @FXML
    private void handleInscrireParticipant() {
        Evenements evenementSelectionne = tableEvenements.getSelectionModel().getSelectedItem();
        if (evenementSelectionne == null) {
            afficherAvertissement("Aucune sélection", "Veuillez sélectionner un événement.");
            return;
        }

        if (gestionEvenements.inscrireParticipant(evenementSelectionne.getId())) {
            afficherInformation("Succès", "Participant inscrit avec succès!");
        } else {
            afficherErreur("Erreur", "Impossible d'inscrire: capacité maximale atteinte!");
        }
    }

    @FXML
    private void handleActualiser() {
        tableEvenements.refresh();
        afficherInformation("Actualisation", "Tableau actualisé!");
    }

    private void chargerDonneesExemple() {
        Evenements ev1 = new Evenements("EV001", "Conférence Tech",
                "Conférence sur les nouvelles technologies",
                LocalDateTime.of(2025, 6, 15, 14, 0), "Salle A", 150);

        Evenements ev2 = new Evenements("EV002", "Formation Java",
                "Formation avancée en programmation Java",
                LocalDateTime.of(2025, 6, 20, 9, 0), "Salle B", 30);

        Evenements ev3 = new Evenements("EV003", "Séminaire Marketing",
                "Stratégies de marketing digital",
                LocalDateTime.of(2025, 6, 25, 10, 30), "Auditorium", 200);

        gestionEvenements.ajouterEvenement(ev1);
        gestionEvenements.ajouterEvenement(ev2);
        gestionEvenements.ajouterEvenement(ev3);
    }

    private void afficherInformation(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherAvertissement(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
