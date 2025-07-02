package org.example.project3.utilities.others;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class FXMLPathConfig {
    private final Properties properties;

    public FXMLPathConfig(String configFile) throws IOException {
        properties = new Properties();
        System.out.println("DEBUG (FXMLPathConfig): Tentativo di caricare il file di configurazione: " + configFile); // DEBUG
        // Tenta di aprire uno stream di input dal file di configurazione specificato
        try (var resource = getClass().getResourceAsStream(configFile)) {
            if (resource == null) {
                // Aggiungiamo un'ulteriore verifica con getResource per vedere l'URL esatto
                URL debugUrl = getClass().getResource(configFile);
                System.out.println("DEBUG (FXMLPathConfig): getResource(" + configFile + ") ha restituito: " + debugUrl); // DEBUG
                throw new IOException("Path non trovato: " + configFile + ". Assicurati che il file sia nel classpath e il percorso sia corretto.");
            }
            // Carica le proprietà dal file di configurazione tramite lo stream
            properties.load(resource);
            System.out.println("DEBUG (FXMLPathConfig): File di configurazione '" + configFile + "' caricato con successo."); // DEBUG
        }
    }

    public String getFXMLPath(String key) {
        String path = properties.getProperty(key);
        System.out.println("DEBUG (FXMLPathConfig): Recuperato percorso per la chiave '" + key + "': " + path); // DEBUG
        return path;
    }

//    public FXMLPathConfig(String configFile) throws IOException {
//        properties = new Properties();
//        // Tenta di aprire uno stream di input dal file di configurazione specificato
//        try (var resource = getClass().getResourceAsStream(configFile)) {
//            if (resource == null) {
//                throw new IOException("Path non trovato " + configFile);
//            }
//            // Carica le proprietà dal file di configurazione tramite lo stream
//            properties.load(resource);
//        }
//    }
//
//    public String getFXMLPath(String key) {
//        return properties.getProperty(key);
//    }
}
