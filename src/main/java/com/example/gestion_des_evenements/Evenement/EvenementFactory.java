package com.example.gestion_des_evenements.Evenement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

abstract class EvenementFactory {
    public abstract Evenement creerEvenement(String id, String nom, LocalDateTime date,
                                             String lieu, int capaciteMax, Map<String, Object> parametres);

}
