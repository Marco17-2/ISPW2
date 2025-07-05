package org.example.project3.dao.full.json;

import org.controlsfx.control.textfield.CustomTextField;
import org.example.project3.dao.CustomerDAO;
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

public class CustomerDAOJSON implements CustomerDAO {
    //Filepath
    private static final String PERSISTENCE_FILE = "src/main/resources/org/example/project3/json.json";
    List<LoggedUser> userList = new ArrayList<>();
    //Singleton
    private static CustomerDAOJSON instance=null;
    // Costruttore privato per evitare la creazione di nuove istanze
    private CustomerDAOJSON() {

    }
    // Metodo per ottenere l'istanza unica
    public static CustomerDAOJSON getInstance() {
        if(instance==null){
            instance=new CustomerDAOJSON();
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
            Printer.errorPrint("Impossibile caricare gli utenti dal file utenti."+ e.getMessage());
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
            Printer.errorPrint("Impossibile salvare l'utente sul file JSON."+ e.getMessage());
        }
    }

    @Override
    public void registerCustomer(Customer customer) throws MailAlreadyExistsException {
        if(insertUser(customer.getCredentials())){
            userList.add(customer);
            addToFile(customer);
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
    public void retrieveCustomer(Customer customer) {
        loadFile();
        Customer app = (Customer) userList.stream().filter(p -> p.getCredentials().getMail().equals(customer.getCredentials().getMail())).findFirst().orElse(null);
        if(app != null){
            setUserParameters(customer, app);
        }
    }


    @Override
    public void removeCustomer(Customer customer) {
        userList.removeIf(p -> p.getCredentials().getMail().equals(customer.getCredentials().getMail()));
        loadFile();
    }
}
