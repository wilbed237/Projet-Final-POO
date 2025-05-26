package com.example.gestion_des_evenements.Evenement;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Evenements {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty nom = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> dateHeure = new SimpleObjectProperty<>();
    private final StringProperty lieu = new SimpleStringProperty();
    private final IntegerProperty capaciteMax = new SimpleIntegerProperty();
    private final IntegerProperty nombreParticipants = new SimpleIntegerProperty();

    public Evenements() {}

    public Evenements(String id, String nom, String description, LocalDateTime dateHeure, String lieu, int capaciteMax) {
        setId(id);
        setNom(nom);
        setDescription(description);
        setDateHeure(dateHeure);
        setLieu(lieu);
        setCapaciteMax(capaciteMax);
        setNombreParticipants(0);
    }

    // Properties pour JavaFX binding
    public StringProperty idProperty() { return id; }
    public StringProperty nomProperty() { return nom; }
    public StringProperty descriptionProperty() { return description; }
    public ObjectProperty<LocalDateTime> dateHeureProperty() { return dateHeure; }
    public StringProperty lieuProperty() { return lieu; }
    public IntegerProperty capaciteMaxProperty() { return capaciteMax; }
    public IntegerProperty nombreParticipantsProperty() { return nombreParticipants; }

    // Getters et Setters
    public String getId() { return id.get(); }
    public void setId(String id) { this.id.set(id); }

    public String getNom() { return nom.get(); }
    public void setNom(String nom) { this.nom.set(nom); }

    public String getDescription() { return description.get(); }
    public void setDescription(String description) { this.description.set(description); }

    public LocalDateTime getDateHeure() { return dateHeure.get(); }
    public void setDateHeure(LocalDateTime dateHeure) { this.dateHeure.set(dateHeure); }

    public String getLieu() { return lieu.get(); }
    public void setLieu(String lieu) { this.lieu.set(lieu); }

    public int getCapaciteMax() { return capaciteMax.get(); }
    public void setCapaciteMax(int capaciteMax) { this.capaciteMax.set(capaciteMax); }

    public int getNombreParticipants() { return nombreParticipants.get(); }
    public void setNombreParticipants(int nombreParticipants) { this.nombreParticipants.set(nombreParticipants); }

    public String getDateHeureFormatee() {
        return getDateHeure() != null ? getDateHeure().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "";
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", getNom(), getDateHeureFormatee(), getLieu());
    }
}
