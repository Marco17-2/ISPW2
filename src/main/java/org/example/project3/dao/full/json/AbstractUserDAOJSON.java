package org.example.project3.dao.full.json;

import org.example.project3.model.Credentials;
import org.example.project3.model.Customer;
import org.example.project3.model.LoggedUser;
import org.example.project3.model.Trainer;
import org.example.project3.utilities.enums.Role;
import org.example.project3.utilities.others.Printer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractUserDAOJSON {

// Necessario per stream

    // Usa un tipo generico se vuoi che la userList contenga il tipo specifico di LoggedUser
        protected static final String PERSISTENCE_FILE = "src/main/resources/org/example/project3/json.json";
        protected List<LoggedUser> userList = new ArrayList<>();

        // Il costruttore può chiamare loadFile se l'inizializzazione dei dati è comune
        protected AbstractUserDAOJSON() {
            // loadFile(); // Decidi se caricare il file nel costruttore base o solo quando serve (lazy loading)
            // Nel tuo codice originale, CredentialsDAOJSON lo fa, TrainerDAOJSON no.
            // Un'opzione è rimuovere le chiamate a loadFile() dai metodi concreti
            // e chiamarlo una volta all'inizio dell'app o al primo accesso al DAO.
            // Per ora, lo lascio commentato qui e le singole DAOs decideranno.
        }

        protected void loadFile() {
            userList.clear(); // Pulisce la lista prima di ricaricare per evitare duplicati
            try (BufferedReader reader = new BufferedReader(new FileReader(PERSISTENCE_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    Credentials credentials = new Credentials(data[0], data[1], Role.valueOf(data[2]));
                    if (credentials.getRole() == null) {
                        throw new IllegalArgumentException("Invalid role");
                    } else {
                        LoggedUser loggedUser = getLoggedUser(credentials, data); // Metodo astratto
                        if (loggedUser != null) {
                            userList.add(loggedUser);
                        }
                    }
                }
            } catch (IOException _) { // Utilizza la variabile e per un migliore debug se necessario
                Printer.errorPrint("Impossibile caricare gli utenti dal file utenti.");
                // e.printStackTrace(); // Utile per debug, ma Printer.errorPrint è sufficiente per l'output finale
            }
        }

        // Metodo astratto che ogni sottoclasse dovrà implementare per creare il tipo di utente specifico
        protected LoggedUser getLoggedUser(Credentials credentials, String[] data){
            LoggedUser loggedUser = null;
            if(credentials.getRole().equals(Role.CLIENT)) {
                loggedUser = new Customer(credentials, data[3], data[4], data[5], Boolean.parseBoolean(data[6]), LocalDate.parse(data[7]));

            }
            else if(credentials.getRole().equals(Role.TRAINER)) {
                loggedUser = new Trainer(credentials, data[3], data[4], data[5], Boolean.parseBoolean(data[6]), LocalDate.parse(data[7]));
            }
            return loggedUser;
        }

        public boolean emailExists(String mail) {
            // Assicurati che userList sia caricata prima di fare la ricerca
            if (userList.isEmpty()) {
                loadFile(); // Carica la lista se è vuota
            }
            return userList.stream().anyMatch(p -> p.getCredentials().getMail().equals(mail));
        }

        public boolean insertUser(Credentials credentials) {
            return !emailExists(credentials.getMail());
        }

        // Anche il metodo addToFile può essere reso generico se la logica di scrittura è comune
        // (potrebbe richiedere un metodo astratto per convertire LoggedUser in stringa)
        protected void addToFile(LoggedUser loggedUser) {
            try (FileWriter writer = new FileWriter(PERSISTENCE_FILE, true)) {// 'true' per append
                String userString = convertUserToString(loggedUser, loggedUser.getCredentials()); // Metodo astratto
                writer.write(userString + "\n");
                loadFile();
            } catch (IOException _) {
                Printer.errorPrint("Impossibile salvare l'utente sul file JSON.");
            }
        }

    protected String convertUserToString(LoggedUser loggedUser,Credentials credentials){
        // Costruisce la stringa da aggqingere al file
        return credentials.getMail() + "," +
                credentials.getPassword() + "," +
                credentials.getRole() + "," +
                loggedUser.getName() + "," +
                loggedUser.getSurname() + "," +
                loggedUser.getGender() + "," +
                loggedUser.isOnline() + "," +
                loggedUser.getBirthday();
    }

    protected void setUserParameters(LoggedUser user, LoggedUser app) {
        user.setName(app.getName());
        user.setSurname(app.getSurname());
        user.setGender(app.getGender());
        user.setOnline(app.isOnline());
        user.setBirthday(app.getBirthday());
    }
}
