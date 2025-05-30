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

    @FXML
    protected void goToLogin(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CustomerHomepage.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/requestModify.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/scheduleDetails.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/customerRegistration.fxml"));
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
