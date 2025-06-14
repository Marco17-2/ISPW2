package org.example.project3.view.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.project3.exceptions.LoadingException;
import org.example.project3.utilities.others.FXMLPathConfig;
import org.example.project3.utilities.others.mappers.Session;
import org.example.project3.beans.*;


import java.io.IOException;
import java.util.List;

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

    @FXML
    protected void goToTrainerDescription(MouseEvent event, CourseBean coursebean){

    }

    @FXML
    protected void goToCourseList(MouseEvent event, List<CourseBean> coursesBean){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CourseList.fxml"));
            fxmlLoader.setControllerFactory(c -> new CourseListGUI(session, fxmlPathConfig));
            Parent root = fxmlLoader.load();
            ((CourseListGUI) fxmlLoader.getController()).printCourseList(coursesBean);
            changeScene(root,event);

        }catch(IOException e){
            throw new LoadingException("Errore durante il caricamento della scena", e);
        }
    }

    @FXML
    protected void goToTrainerDetail(MouseEvent event, CourseBean coursebean){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TrainerDetail.fxml"));
            fxmlLoader.setControllerFactory(c -> new TrainerDetailGUI(session, fxmlPathConfig));
            Parent root = fxmlLoader.load();
            ((TrainerDetailGUI) fxmlLoader.getController()).TrainerDetail(coursebean);
            changeScene(root, event);

        }catch(IOException e){
            throw new LoadingException("Errore durante il caricamento della scena", e);
        }
    }

    @FXML
    protected void goToCourseReservationRequest(MouseEvent event, List<ReservationBean> reservationReqBean){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/RservationReq.fxml"));
            fxmlLoader.setControllerFactory(c -> new ReservationReqGUI(session, fxmlPathConfig));
            Parent root = fxmlLoader.load();
            ((ReservationReqGUI)fxmlLoader.getController()).initializeObserver();
            ((ReservationReqGUI) fxmlLoader.getController()).loadReservationReq(reservationReqBean);
            changeScene(root, event);

        }catch(IOException e){
            throw new LoadingException("Errore durante il caricamento della scena", e);
        }
    }

    @FXML
    protected void goToCustomerDetail(MouseEvent event, ReservationBean reservationBean){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CustomerDetail.fxml"));
            fxmlLoader.setControllerFactory(c -> new CustomerDetailGUI(session, fxmlPathConfig));
            Parent root = fxmlLoader.load();
            ((CustomerDetailGUI) fxmlLoader.getController()).loadCustomerDetail(reservationBean);
            changeScene(root, event);

        }catch(IOException e){
            throw new LoadingException("Errore durante il caricamento della scena", e);
        }
    }


    //aggiugnere la chiamata di questa funzione nella schermata login trainer

    @FXML
    protected void goToTrainerHome(MouseEvent event){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TrainerHomepage.fxml"));
            fxmlLoader.setControllerFactory(c -> new CustomerDetailGUI(session, fxmlPathConfig));
            Parent root = fxmlLoader.load();
            changeScene(root, event);

        }catch(IOException e){
            throw new LoadingException("Errore durante il caricamento della scena", e);
        }
    }

    //Metodo per cambiare finestra
    protected void changeScene(Parent root, MouseEvent event) {
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
