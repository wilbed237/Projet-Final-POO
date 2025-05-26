package com.example.gestion_des_evenements.Interface;

public interface EvenementObservable {
    void ajouterObserver(ParticipantObserver observer);
    void supprimerObserver(ParticipantObserver observer);
    void notifierObservers(String message);
}
