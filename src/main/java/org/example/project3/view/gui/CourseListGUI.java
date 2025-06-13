package org.example.project3.view.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import org.example.project3.beans.*;
import org.example.project3.controller.CourseListController;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.model.Course;
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

import static org.controlsfx.control.action.ActionUtils.createButton;

public class CourseListGUI extends CommonGUI implements Observer{

    protected CourseListGUI(Session session, FXMLPathConfig fxmlPathConfig) {
        super(session, fxmlPathConfig);
    }

    @FXML
    private TableView <CourseBean> listCourse;
    @FXML
    private TableColumn<CourseBean, String> courseNameColumn;
    @FXML
    private TableColumn<CourseBean, Void> trainerButtonColumn;
    @FXML
    private TableColumn<CourseBean, String> slotsColumn;
    @FXML
    private TableColumn<CourseBean, String> remainingColumn;
    @FXML
    private TableColumn<CourseBean, String> durationColumn;
    @FXML
    private TableColumn<CourseBean, String> dayColumn;
    @FXML
    private TableColumn<CourseBean, String> hourColumn;
    @FXML
    private TableColumn<CourseBean, Void> buttonColumn;
    @FXML
    private Label message; // da aggiungere
    @FXML
    private Button goBack;


    //private final RequestManagerConcreteSubject requestManagerConcreteSubject;   ConcreteSubject


    private TableCell<CourseBean, Void> createButtonCell(String buttonText){
        return new TableCell<>(){
            private final Button button = createButton(buttonText);
            private Button createButton(String buttontext){
                Button btn = new Button(buttonText);
                btn.setOnMouseClicked(event -> {
                    CourseBean courseBean = getTableView().getItems().get(getIndex());
                    manageReservationReq(courseBean);
                });
                return btn;
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        };
    }

    private TableCell<CourseBean, Void> createViewCell(String buttonText) {

        return new TableCell<>() {
            private final Button button = createButton(buttonText);

            private Button createButton(String buttontext) {
                Button btn = new Button(buttonText);
                btn.setOnMouseClicked(event -> {
                    CourseBean courseBean = getTableView().getItems().get(getIndex());
                    goToTrainerDetail(event, courseBean);
                });
                return btn;
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        };
    }



    @FXML
    public void printCourseList(List<CourseBean> courseBeans){

        //Associazione colonne ai campi
        ObservableList<CourseBean> courseBeanList = FXCollections.observableList(courseBeans);
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        slotsColumn.setCellValueFactory(new PropertyValueFactory<>("slots"));
        remainingColumn.setCellValueFactory(new PropertyValueFactory<>("remaining"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        hourColumn.setCellValueFactory(new PropertyValueFactory<>("hour"));
        buttonColumn.setCellFactory(param -> createButtonCell("Accetta"));
        trainerButtonColumn.setCellFactory(param -> createViewCell("Detail"));

        listCourse.setItems(courseBeanList);

    }

    private void manageReservationReq(CourseBean courseBean){

        CustomerBean customerBean = (CustomerBean) session.getUser();
        String day = courseBean.getDay();
        String hour = courseBean.getHour();
        ReservationBean reservationReqBean = new ReservationBean(customerBean, courseBean, day, hour);
        CourseListController courseListController = new CourseListController();
        courseListController.sendReservationReq(reservationReqBean);
        message.setText("Richiesta inviata con successo");
        message.setVisible(true);

    }


    @Override
    public void update(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invio richiesta");
        alert.setHeaderText(null);
        alert.setContentText("Richiesta inviata con successo");
        alert.showAndWait();
    }


    @FXML
    public void goBack(MouseEvent event){
       goToHomepage(event);
    }
}
