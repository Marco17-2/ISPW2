package org.example.project3.view.gui;

import javafx.collections.FXCollections;
import org.example.project3.beans.*;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.model.Reservation;
import org.example.project3.utilities.others.FXMLPathConfig;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.example.project3.utilities.others.mappers.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.project3.patterns.observer.Observer;

import javafx.scene.control.*;
import java.util.List;
import java.util.ArrayList;

public class ReservationReqGUI extends CommonGUI implements Observer {

    protected ReservationReqGUI(Session session, FXMLPathConfig fxmlPathConfig) {
        super(session, fxmlPathConfig);
    }

    // name course date hour detail accept refuse

    @FXML
    private TableView<Reservation> reservationList;
    @FXML
    private TableColumn<Reservation, String> customerName;
    @FXML
    private TableColumn<Reservation, String> CourseName;
    @FXML
    private TableColumn<Reservation, String> date;
    @FXML
    private TableColumn<Reservation, String> hour;
    @FXML
    private TableColumn<Reservation, Void> acceptButton;
    @FXML
    private TableColumn<Reservation, Void> refuseButton;


    /*

        // Metodo per inizializzare l'observer
    public void initializeObserver() {
        requestManagerConcreteSubject.addObserver(this); // Aggiungi l'osservatore
    }

     */



    @Override
    public void update(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invio richiesta");
        alert.setHeaderText(null);
        alert.setContentText("Richiesta inviata con successo");
        alert.showAndWait();
    }

    @FXML
    protected void retrieveReservationReq(MouseEvent event){
        try{
            List<ReservationBean> reservationReqBean = new ArrayList<>();

            // da implementare il controller;
            // TrainerController.retrieveReservation(reservationReqBean);

            goToCourseReservationRequest(event, reservationReqBean);
        }catch(NoResultException exceprion){

            // errorMessage.setText(exception.getMessage());
            // errorMessage.setVisible(true);
            //implementare label
        }

    }

}
