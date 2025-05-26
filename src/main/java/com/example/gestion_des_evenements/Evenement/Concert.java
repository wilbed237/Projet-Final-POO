package com.example.gestion_des_evenements.Evenement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

class Concert extends Evenement {
    private String artiste;
    private String genreMusical;

    public Concert() {
        super();
    }

    public Concert(String id, String nom, LocalDateTime date, String lieu, int capaciteMax,
                   String artiste, String genreMusical) {
        super(id, nom, date, lieu, capaciteMax);
        this.artiste = artiste;
        this.genreMusical = genreMusical;
    }

    @Override
    public void afficherDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("=== CONCERT ===");
        System.out.println("ID: " + id);
        System.out.println("Nom: " + nom);
        System.out.println("Artiste: " + artiste);
        System.out.println("Genre: " + genreMusical);
        System.out.println("Date: " + date.format(formatter));
        System.out.println("Lieu: " + lieu);
        System.out.println("Participants: " + participants.size() + "/" + capaciteMax);
    }

    // Getters et Setters
    public String getArtiste() { return artiste; }
    public void setArtiste(String artiste) { this.artiste = artiste; }

    public String getGenreMusical() { return genreMusical; }
    public void setGenreMusical(String genreMusical) { this.genreMusical = genreMusical; }
}
