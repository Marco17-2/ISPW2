package org.example.project3.view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.example.project3.beans.CustomerBean;
import org.example.project3.beans.LoggedUserBean;
import org.example.project3.controller.LoginController;
import org.example.project3.controller.UserRegistrationController;
import org.example.project3.exceptions.EmptyFieldException;
import org.example.project3.exceptions.LoginAndRegistrationException;
import org.example.project3.exceptions.MailAlreadyExistsException;
import org.example.project3.exceptions.WrongEmailOrPasswordException;
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
    TextField genere;
    @FXML
    DatePicker dataNascita;
    @FXML
    Label error;

    private final UserRegistrationController registrationController = new UserRegistrationController();
    private final LoginController loginController = new LoginController();

    @FXML
    public void checkFields(TextField fields[], PasswordField password, DatePicker dataNascita, Label error) throws EmptyFieldException {
        for(TextField field : fields) {
            if(field.getText().isEmpty()) {
                throw new EmptyFieldException("Compila tutti i campi");
            }
        }
        if(password.getText().isEmpty()) {
            throw new EmptyFieldException("Inserisci la password");
        }
        if(dataNascita.getValue() == null) {
            throw new EmptyFieldException("Inserisci la data di nascita");
        }
    }

    @FXML
    protected void register(MouseEvent event, LoggedUserBean loggedUserBean) {
        try{
            TextField[] fields={nome,cognome,email,genere};
            PasswordField password = this.password;
            DatePicker dataNascita = this.dataNascita;
            checkFields(fields,password,dataNascita,error);
            if(isValidMail(email.getText(),error)){
                return;
            }
            registrationController.registerUser(loggedUserBean);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registrazione");
            alert.setHeaderText(null);
            alert.setContentText("Registrato con successo");
            alert.showAndWait();

            loginController.login(loggedUserBean.getCredentialsBean());
            session.setUser(loggedUserBean);
            goToCustomerHomepage(event);
        }catch(MailAlreadyExistsException| WrongEmailOrPasswordException| EmptyFieldException| LoginAndRegistrationException e){
            error.setText(e.getMessage());
            error.setVisible(true);
        }
    }

    public static boolean isValidMail(String mail, Label errorMessage) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!mail.matches(emailRegex)) {
            errorMessage.setText("Mail non valida");
            errorMessage.setVisible(true);
            return true;
        }
        return false;
    }
}
