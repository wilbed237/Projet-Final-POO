package com.example.gestion_des_evenements.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GestionEvenementsApp extends Application {

    public GestionEvenementsApp() {
        super();
    }


    private final GestionEvenements gestionEvenements = new GestionEvenements();
    private final TableView<Evenement> tableEvenements = new TableView<>();
    private final ObservableList<Evenement> evenementsData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestion des Événements");

        // Configuration du tableau
        configurerTableau();

        // Boutons
        Button btnAjouter = new Button("Ajouter Événement");
        Button btnModifier = new Button("Modifier");
        Button btnSupprimer = new Button("Supprimer");
        Button btnInscrire = new Button("Inscrire Participant");
        Button btnActualiser = new Button("Actualiser");

        // Actions des boutons
        btnAjouter.setOnAction(e -> ouvrirDialogueAjout());
        btnModifier.setOnAction(e -> ouvrirDialogueModification());
        btnSupprimer.setOnAction(e -> supprimerEvenement());
        btnInscrire.setOnAction(e -> inscrireParticipant());
        btnActualiser.setOnAction(e -> actualiserTableau());

        // Style des boutons
        btnAjouter.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        btnModifier.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnSupprimer.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        btnInscrire.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        btnActualiser.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");

        // Layout des boutons
        HBox boutonsBox = new HBox(10);
        boutonsBox.setAlignment(Pos.CENTER);
        boutonsBox.getChildren().addAll(btnAjouter, btnModifier, btnSupprimer, btnInscrire, btnActualiser);

        // Layout principal
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titre = new Label("Système de Gestion des Événements");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        root.getChildren().addAll(titre, tableEvenements, boutonsBox);

        // Scène
        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add("data:text/css," +
                ".table-view { -fx-background-color: #fafafa; }" +
                ".table-view .column-header { -fx-background-color: #e0e0e0; -fx-font-weight: bold; }");

        primaryStage.setScene(scene);
        primaryStage.show();

        // Charger des données d'exemple
        chargerDonneesExemple();
    }

    private void configurerTableau() {
        // Colonnes du tableau
        TableColumn<Evenement, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(80);

        TableColumn<Evenement, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(200);

        TableColumn<Evenement, String> colDescription = new TableColumn<>("Description");
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDescription.setPrefWidth(250);

        TableColumn<Evenement, String> colDate = new TableColumn<>("Date/Heure");
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateHeureFormatee"));
        colDate.setPrefWidth(150);

        TableColumn<Evenement, String> colLieu = new TableColumn<>("Lieu");
        colLieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        colLieu.setPrefWidth(150);

        TableColumn<Evenement, Integer> colCapacite = new TableColumn<>("Capacité Max");
        colCapacite.setCellValueFactory(new PropertyValueFactory<>("capaciteMax"));
        colCapacite.setPrefWidth(100);

        TableColumn<Evenement, Integer> colParticipants = new TableColumn<>("Participants");
        colParticipants.setCellValueFactory(new PropertyValueFactory<>("nombreParticipants"));
        colParticipants.setPrefWidth(100);

        tableEvenements.getColumns().addAll(colId, colNom, colDescription, colDate, colLieu, colCapacite, colParticipants);
        tableEvenements.setItems(evenementsData);
    }

    private void ouvrirDialogueAjout() {
        DialogueEvenement dialogue = new DialogueEvenement();
        dialogue.showAndWait().ifPresent(evenement -> {
            if (gestionEvenements.ajouterEvenement(evenement)) {
                actualiserTableau();
                afficherMessage("Événement ajouté avec succès!", Alert.AlertType.INFORMATION);
            } else {
                afficherMessage("Erreur: Un événement avec cet ID existe déjà!", Alert.AlertType.ERROR);
            }
        });
    }

    private void ouvrirDialogueModification() {
        Evenement evenementSelectionne = tableEvenements.getSelectionModel().getSelectedItem();
        if (evenementSelectionne == null) {
            afficherMessage("Veuillez sélectionner un événement à modifier.", Alert.AlertType.WARNING);
            return;
        }

        DialogueEvenement dialogue = new DialogueEvenement(evenementSelectionne);
        dialogue.showAndWait().ifPresent(evenement -> {
            if (gestionEvenements.modifierEvenement(evenementSelectionne.getId(), evenement)) {
                actualiserTableau();
                afficherMessage("Événement modifié avec succès!", Alert.AlertType.INFORMATION);
            } else {
                afficherMessage("Erreur lors de la modification!", Alert.AlertType.ERROR);
            }
        });
    }

    private void supprimerEvenement() {
        Evenement evenementSelectionne = tableEvenements.getSelectionModel().getSelectedItem();
        if (evenementSelectionne == null) {
            afficherMessage("Veuillez sélectionner un événement à supprimer.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Supprimer l'événement");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cet événement ?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (gestionEvenements.supprimerEvenement(evenementSelectionne.getId())) {
                    actualiserTableau();
                    afficherMessage("Événement supprimé avec succès!", Alert.AlertType.INFORMATION);
                }
            }
        });
    }

    private void inscrireParticipant() {
        Evenement evenementSelectionne = tableEvenements.getSelectionModel().getSelectedItem();
        if (evenementSelectionne == null) {
            afficherMessage("Veuillez sélectionner un événement.", Alert.AlertType.WARNING);
            return;
        }

        if (gestionEvenements.inscrireParticipant(evenementSelectionne.getId())) {
            actualiserTableau();
            afficherMessage("Participant inscrit avec succès!", Alert.AlertType.INFORMATION);
        } else {
            afficherMessage("Impossible d'inscrire: capacité maximale atteinte!", Alert.AlertType.ERROR);
        }
    }

    private void actualiserTableau() {
        evenementsData.clear();
        evenementsData.addAll(gestionEvenements.obtenirTousLesEvenements());
    }

    private void afficherMessage(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void chargerDonneesExemple() {
        Evenement ev1 = new Evenement("EV001", "Conférence Tech", "Conférence sur les nouvelles technologies",
                LocalDateTime.of(2025, 6, 15, 14, 0), "Salle A", 150);
        Evenement ev2 = new Evenement("EV002", "Formation Java", "Formation avancée en programmation Java",
                LocalDateTime.of(2025, 6, 20, 9, 0), "Salle B", 30);
        Evenement ev3 = new Evenement("EV003", "Séminaire Marketing", "Stratégies de marketing digital",
                LocalDateTime.of(2025, 6, 25, 10, 30), "Auditorium", 200);

        gestionEvenements.ajouterEvenement(ev1);
        gestionEvenements.ajouterEvenement(ev2);
        gestionEvenements.ajouterEvenement(ev3);

        actualiserTableau();
    }

    // Classe interne pour le dialogue d'ajout/modification
    private class DialogueEvenement extends Dialog<Evenement> {
        private TextField txtId = new TextField();
        private TextField txtNom = new TextField();
        private TextArea txtDescription = new TextArea();
        private TextField txtDateHeure = new TextField();
        private TextField txtLieu = new TextField();
        private TextField txtCapacite = new TextField();

        public DialogueEvenement() {
            this(null);
        }

        public DialogueEvenement(Evenement evenement) {
            setTitle(evenement == null ? "Ajouter un Événement" : "Modifier l'Événement");
            setHeaderText(null);

            // Configuration des champs
            txtDescription.setPrefRowCount(3);
            txtDateHeure.setPromptText("jj/MM/aaaa HH:mm");

            if (evenement != null) {
                txtId.setText(evenement.getId());
                txtId.setDisable(true); // ID non modifiable
                txtNom.setText(evenement.getNom());
                txtDescription.setText(evenement.getDescription());
                txtDateHeure.setText(evenement.getDateHeureFormatee());
                txtLieu.setText(evenement.getLieu());
                txtCapacite.setText(String.valueOf(evenement.getCapaciteMax()));
            }

            // Layout du formulaire
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20));

            grid.add(new Label("ID:"), 0, 0);
            grid.add(txtId, 1, 0);
            grid.add(new Label("Nom:"), 0, 1);
            grid.add(txtNom, 1, 1);
            grid.add(new Label("Description:"), 0, 2);
            grid.add(txtDescription, 1, 2);
            grid.add(new Label("Date/Heure:"), 0, 3);
            grid.add(txtDateHeure, 1, 3);
            grid.add(new Label("Lieu:"), 0, 4);
            grid.add(txtLieu, 1, 4);
            grid.add(new Label("Capacité Max:"), 0, 5);
            grid.add(txtCapacite, 1, 5);

            getDialogPane().setContent(grid);

            // Boutons
            ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            getDialogPane().getButtonTypes().addAll(buttonTypeOk, buttonTypeCancel);

            // Validation et conversion
            setResultConverter(dialogButton -> {
                if (dialogButton == buttonTypeOk) {
                    try {
                        String id = txtId.getText().trim();
                        String nom = txtNom.getText().trim();
                        String description = txtDescription.getText().trim();
                        String lieu = txtLieu.getText().trim();

                        if (id.isEmpty() || nom.isEmpty() || lieu.isEmpty()) {
                            afficherMessage("Tous les champs obligatoires doivent être remplis!", Alert.AlertType.ERROR);
                            return null;
                        }

                        LocalDateTime dateHeure = LocalDateTime.parse(txtDateHeure.getText().trim(),
                                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                        int capacite = Integer.parseInt(txtCapacite.getText().trim());

                        Evenement nouvelEvenement = new Evenement(id, nom, description, dateHeure, lieu, capacite);
                        if (evenement != null) {
                            nouvelEvenement.setNombreParticipants(evenement.getNombreParticipants());
                        }
                        return nouvelEvenement;

                    } catch (DateTimeParseException e) {
                        afficherMessage("Format de date invalide! Utilisez: jj/MM/aaaa HH:mm", Alert.AlertType.ERROR);
                        return null;
                    } catch (NumberFormatException e) {
                        afficherMessage("La capacité doit être un nombre valide!", Alert.AlertType.ERROR);
                        return null;
                    }
                }
                return null;
            });

            // Configuration de la fenêtre
            initModality(Modality.APPLICATION_MODAL);
            setResizable(true);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}