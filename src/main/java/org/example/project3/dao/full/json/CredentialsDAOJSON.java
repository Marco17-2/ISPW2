package org.example.project3.dao.full.json;

import org.example.project3.dao.CredentialsDAO;
import org.example.project3.model.*;
import org.example.project3.utilities.enums.Role;
import org.example.project3.exceptions.MailAlreadyExistsException;
import org.example.project3.exceptions.WrongEmailOrPasswordException;
import org.example.project3.utilities.others.Printer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CredentialsDAOJSON implements CredentialsDAO {
        //Filepath
        private static final String PERSISTENCE_FILE = "src/main/resources/org/example/project3/json.json";
        List<LoggedUser> userList = new ArrayList<>();
    //Singleton
    private static CredentialsDAOJSON instance=null;
    // Costruttore privato per evitare la creazione di nuove istanze
    private CredentialsDAOJSON() {
        loadFile();
    }
    // Metodo per ottenere l'istanza unica
    public static CredentialsDAOJSON getInstance() {
        if(instance==null){
            instance=new CredentialsDAOJSON();
        }
        return instance;
    }

        private void loadFile() {
            //qui leggo il file e carico i dati in loggedUserHashMap
            try (BufferedReader reader = new BufferedReader(new FileReader(PERSISTENCE_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    Credentials credentials = new Credentials(data[0], data[1], Role.valueOf(data[2]));
                    if(credentials.getRole() == null)
                        throw new IllegalArgumentException("Invalid role");
                    else{
                        LoggedUser loggedUser = getLoggedUser(credentials, data);
                        userList.add(loggedUser);
                    }
                }
            } catch (IOException e) {
                Printer.errorPrint("Impossibile caricare gli utenti dal file utenti.");
            }
        }

    private static LoggedUser getLoggedUser(Credentials credentials, String[] data) {
        LoggedUser loggedUser = null;
        if(credentials.getRole().equals(Role.CLIENT)) {
            loggedUser = new Customer(credentials, data[3], data[4], data[5], Boolean.parseBoolean(data[6]), LocalDate.parse(data[7]));

        }
        else if(credentials.getRole().equals(Role.TRAINER)) {
            loggedUser = new Trainer(credentials, data[3], data[4], data[5], Boolean.parseBoolean(data[6]), LocalDate.parse(data[7]));
        }
        return loggedUser;
    }

    @Override
    public boolean emailExists(String mail) {
        return userList.stream().anyMatch(p -> p.getCredentials().getMail().equals(mail));
    }

    @Override
    public boolean insertUser(Credentials credentials) {
        return !emailExists(credentials.getMail());
    }

    @Override
    public void login(Credentials credentials) throws WrongEmailOrPasswordException {
        loadFile();
        LoggedUser  loggedUser  = userList.stream().filter(p -> p.getCredentials().getMail().equals(credentials.getMail())).findFirst().orElse(null);
        if (loggedUser == null || !loggedUser.getCredentials().getPassword().equals(credentials.getPassword())) {
            throw new WrongEmailOrPasswordException("Mail o password errati");
        }
        credentials.setRole(loggedUser.getCredentials().getRole());
    }



}
