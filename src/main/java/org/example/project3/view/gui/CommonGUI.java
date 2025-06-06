package org.example.project3.view.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.project3.exceptions.LoadingException;
import org.example.project3.utilities.others.FXMLPathConfig;
import org.example.project3.utilities.others.mappers.Session;

import java.io.IOException;

public abstract class CommonGUI {
    protected Session session;
    protected FXMLPathConfig fxmlPathConfig;

    protected CommonGUI(Session session, FXMLPathConfig fxmlPathConfig) {
        this.session = session;
        this.fxmlPathConfig = fxmlPathConfig;
    }

    protected static final String CUSTOMER_HOMEPAGE="CUSTOMER_HOMEPAGE";
    protected static final String CUSTOMER_REGISTRATION="CUSTOMER_REGISTRATION";
    protected static final String HOMEPAGE="HOMEPAGE";
    protected static final String LOGIN="LOGIN";
    protected static final String LOGIN_AND_REGISTER="LOGIN_AND_REGISTER";
    protected static final String REQUEST_MODIFY="REQUEST_MODIFY";
    protected static final String SCHEDULE_DETAILS="SCHEDULE_DETAILS";


    @FXML
    protected void goToLogin(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(LOGIN));
            fxmlLoader.setControllerFactory( c->new LoginGUI(fxmlPathConfig, session));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            throw new LoadingException("Errore durante il caricamento della scena", e);
        }
    }

    @FXML
    protected void goToCustomerHomepage(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CUSTOMER_HOMEPAGE));
            fxmlLoader.setControllerFactory( c->new CustomerHomepageGUI(fxmlPathConfig, session));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            throw new LoadingException("Errore durante il caricamento della scena", e);
        }
    }

    @FXML
    protected void goToRequestSchedule(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(REQUEST_MODIFY));
            fxmlLoader.setControllerFactory( c->new RequestScheduleGUI(fxmlPathConfig, session));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            throw new LoadingException("Errore durante il caricamento della scena", e);
        }
    }

    @FXML
    protected void goToRequestExercise(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(SCHEDULE_DETAILS));
            fxmlLoader.setControllerFactory( c->new RequestExerciseGUI(fxmlPathConfig, session));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            throw new LoadingException("Errore durante il caricamento della scena", e);
        }
    }

    @FXML
    protected void goToRegistration(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CUSTOMER_REGISTRATION));
            fxmlLoader.setControllerFactory( c->new RegistrationGUI(fxmlPathConfig, session));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            throw new LoadingException("Errore durante il caricamento della scena", e);
        }
    }

    @FXML
    protected void goToLoginAndRegister(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(LOGIN_AND_REGISTER));
            fxmlLoader.setControllerFactory( c->new RegistrationGUI(fxmlPathConfig, session));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            throw new LoadingException("Errore durante il caricamento della scena", e);
        }
    }

    @FXML
    protected void goToHomepage(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(HOMEPAGE));
            fxmlLoader.setControllerFactory( c->new RegistrationGUI(fxmlPathConfig, session));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            throw new LoadingException("Errore durante il caricamento della scena", e);
        }
    }

}
