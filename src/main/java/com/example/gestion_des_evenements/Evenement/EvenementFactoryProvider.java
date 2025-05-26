package com.example.gestion_des_evenements.Evenement;

class EvenementFactoryProvider {
    public static EvenementFactory getFactory(String type) {
        switch (type.toLowerCase()) {
            case "conference":
                return new ConferenceFactory();
            case "concert":
                return new ConcertFactory();
            default:
                throw new IllegalArgumentException("Type d'événement non supporté: " + type);
        }
    }
}

