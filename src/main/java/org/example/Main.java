package org.example;

public class Main { // Der äußere Rahmen für die Klasse hat gefehlt!

    // Die main-Methode braucht zwingend (String[] args) in den Klammern
    public static void main(String[] args) {

        // Holt sich das Log-Tagebuch vom Zoo
        java.util.logging.Logger zooLogger = java.util.logging.Logger.getLogger(org.example.Zoo.class.getName());

        // Schaltet das Level um auf FINE, damit auch die Erfolgsmeldungen gedruckt werden
        zooLogger.setLevel(java.util.logging.Level.FINE);

        // Hier drunter kannst du jetzt deine Zoo-Objekte testen, wenn du willst!
    }
}