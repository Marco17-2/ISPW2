package org.example.project3.view.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.example.project3.beans.CredentialsBean;
import org.example.project3.beans.CustomerBean;
import org.example.project3.beans.LoggedUserBean;
import org.example.project3.controller.LoginController;
import org.example.project3.controller.UserRegistrationController;
import org.example.project3.exceptions.EmptyFieldException;
import org.example.project3.exceptions.LoginAndRegistrationException;
import org.example.project3.exceptions.MailAlreadyExistsException;
import org.example.project3.exceptions.WrongEmailOrPasswordException;
import org.example.project3.utilities.enums.Role;
import org.example.project3.utilities.others.FXMLPathConfig;
import org.example.project3.utilities.others.mappers.Session;

import java.io.IOException;

public class RegistrationGUI extends CommonGUI {
    protected RegistrationGUI(FXMLPathConfig fxmlPathConfig, Session session) { super(session, fxmlPathConfig); }

    @FXML
    TextField nome;
    @FXML
    TextField cognome;
    @FXML
    TextField email;
    @FXML
    PasswordField password;
    @FXML
    ChoiceBox genere;
    @FXML
    DatePicker dataNascita;
    @FXML
    Text error;

    private final UserRegistrationController registrationController = new UserRegistrationController();
    private final LoginController loginController = new LoginController();


    @FXML
    public void initialize() {
        // Definisci le stringhe per la ChoiceBox
        ObservableList<String> opzioniGenere = FXCollections.observableArrayList(
                "Maschio",
                "Femmina",
                "Altro" // O qualsiasi altra opzione tu voglia
        );

        // Setta gli elementi nella ChoiceBox
        genere.setItems(opzioniGenere);
    }

    @FXML
    public void checkFields(TextField fields[], PasswordField password,ChoiceBox genere, DatePicker dataNascita, javafx.scene.text.Text error) throws EmptyFieldException {
        for(TextField field : fields) {
            if(field.getText().isEmpty()) {
                throw new EmptyFieldException("Compila tutti i campi");
            }
        }
        if(password.getText().isEmpty()) {
            throw new EmptyFieldException("Inserisci la password");
        }
        if(genere.getValue() == null) {
            throw new EmptyFieldException("Inserisci il genere");
        }
        if(dataNascita.getValue() == null) {
            throw new EmptyFieldException("Inserisci la data di nascita");
        }
    }

    @FXML
    protected void register(MouseEvent event) {
        try{
            CustomerBean customerBean = new CustomerBean(new CredentialsBean(email.getText(), password.getText(), Role.CLIENT),nome.getText(), cognome.getText(), genere.getValue().toString(), true,dataNascita.getValue());
            TextField[] fields={nome,cognome,email};
            ChoiceBox genere = this.genere;
            PasswordField password = this.password;
            DatePicker dataNascita = this.dataNascita;
            checkFields(fields,password,genere,dataNascita,error);
            if(isValidMail(email.getText(),error)){
                return;
            }
            registrationController.registerUser(customerBean);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registrazione");
            alert.setHeaderText(null);
            alert.setContentText("Registrato con successo");
            alert.showAndWait();

            loginController.login(customerBean.getCredentialsBean());
            session.setUser(customerBean);
            goToCustomerHomepage(event);
        }catch(MailAlreadyExistsException| WrongEmailOrPasswordException| EmptyFieldException| LoginAndRegistrationException e){
            error.setText(e.getMessage());
            error.setVisible(true);
        }
    }

    public static boolean isValidMail(String mail, javafx.scene.text.Text errorMessage) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!mail.matches(emailRegex)) {
            errorMessage.setText("Mail non valida");
            errorMessage.setVisible(true);
            return true;
        }
        return false;
    }
}
