package org.example.project3.dao.full.json;

import org.example.project3.dao.TrainerDAO;
import org.example.project3.exceptions.MailAlreadyExistsException;
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

public class TrainerDAOJSON implements TrainerDAO {

    //Filepath
    private static final String PERSISTENCE_FILE = "src/main/resources/org/example/project3/json.json";
    List<LoggedUser> userList = new ArrayList<>();
    //Singleton
    private static TrainerDAOJSON instance=null;
    // Costruttore privato per evitare la creazione di nuove istanze
    private TrainerDAOJSON() {

    }
    // Metodo per ottenere l'istanza unica
    public static TrainerDAOJSON getInstance() {
        if(instance==null){
            instance=new TrainerDAOJSON();
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


    private void addToFile(LoggedUser  loggedUser ) {
        try (FileWriter writer = new FileWriter(PERSISTENCE_FILE, true)) { // 'true' to append
            Credentials credentials = loggedUser.getCredentials();

            // Costruisce la stringa da aggqingere al file
            String sb = credentials.getMail() + "," +
                    credentials.getPassword() + "," +
                    credentials.getRole() + "," +
                    loggedUser.getName() + "," +
                    loggedUser.getSurname() + "," +
                    loggedUser.getGender() + "," +
                    loggedUser.isOnline() + "," +
                    loggedUser.getBirthday();

            writer.write(sb + "\n"); //Aggiunge l'utente al file
            loadFile(); // Ricarica i dati dopo aver aggiunto un nuovo utente
        } catch (IOException e) {
            Printer.errorPrint("Impossibile salvare l'utente sul file JSON.");
        }
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
    public void registerTrainer(Trainer trainer) throws MailAlreadyExistsException {
        if(insertUser(trainer.getCredentials())){
            userList.add(trainer);
            addToFile(trainer);
        }
        else
            throw new MailAlreadyExistsException("Mail già registrata");
    }


    private void setUserParameters(LoggedUser user, LoggedUser app) {
        user.setName(app.getName());
        user.setSurname(app.getSurname());
        user.setGender(app.getGender());
        user.setOnline(app.isOnline());
        user.setBirthday(app.getBirthday());
    }

    @Override
    public void retrieveTrainer(Trainer trainer) {
        loadFile();
        Trainer app = (Trainer) userList.stream().filter(p -> p.getCredentials().getMail().equals(trainer.getCredentials().getMail())).findFirst().orElse(null);
        if (app != null) {
            setUserParameters(trainer, app);
        }
    }

}
