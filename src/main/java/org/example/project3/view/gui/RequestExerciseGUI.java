package org.example.project3.view.gui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.project3.beans.ExerciseBean;
import org.example.project3.beans.RequestBean;
import org.example.project3.beans.ScheduleBean;
import org.example.project3.controller.RequestModifyController;
import org.example.project3.exceptions.LoadingException;
import org.example.project3.model.Request;
import org.example.project3.model.Schedule;
import org.example.project3.patterns.observer.Observer;
import org.example.project3.patterns.observer.RequestManagerConcreteSubject;
import org.example.project3.utilities.others.FXMLPathConfig;
import org.example.project3.utilities.others.mappers.Session;

import java.io.IOException;
import java.util.List;

public class RequestExerciseGUI extends CommonGUI implements Observer {

    private final RequestManagerConcreteSubject requestManagerConcreteSubject;
    public RequestExerciseGUI(FXMLPathConfig fxmlPathConfig, Session session) { super(session, fxmlPathConfig);
    this.requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();}

    @FXML
    Button indietro;

    @FXML
    TextField ricerca;

    @FXML
    Button invia;

    @FXML
    TextField motivazione;

    @FXML
    TableView<ExerciseBean> SheduleChoice;

    @FXML
    TableColumn<ExerciseBean, Long> ID;

    @FXML
    TableColumn<ExerciseBean, String> Name;

    @FXML
    TableColumn<ExerciseBean, String> Description;

    @FXML
    TableColumn<ExerciseBean, Integer> Series;

    @FXML
    TableColumn<ExerciseBean, Integer> Reps;

    @FXML
    TableColumn<ExerciseBean, String> RestTime;

    @FXML
    TableColumn<ExerciseBean, Void> Check;

    public void initializeObserver() {requestManagerConcreteSubject.addObserver(this);}

    private final RequestModifyController requestModifyController = new RequestModifyController();

    private final ToggleGroup rowToggleGroup = new ToggleGroup();

    private RequestBean requestBean;

    private TableCell<ExerciseBean, Void> createButtonCell() {
        return new TableCell<>() {
            private final RadioButton radioButton = new RadioButton();
            {
                radioButton.setToggleGroup(rowToggleGroup);

                // Quando il radio viene selezionato, salvi l'oggetto selezionato
                radioButton.setOnAction(e -> {
                    if (getIndex() >= 0 && getIndex() < getTableView().getItems().size()) {
                        ExerciseBean selected = getTableView().getItems().get(getIndex());
                        // Salva la selezione (può essere in una variabile esterna o controller)
                        requestBean.setExercise(selected);// ad esempio variabile del controller
                        motivazione.setVisible(true);
                        invia.setVisible(true);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    ExerciseBean current = getTableView().getItems().get(getIndex());

                    // Se questa riga è quella selezionata, evidenziala
                    if (current.equals(requestBean.getExerciseBean())) {
                        radioButton.setSelected(true);
                    } else {
                        radioButton.setSelected(false);
                    }

                    setGraphic(radioButton);
                }
            }
        };
    }

    @FXML
    public void loadExercises(RequestBean request) {
        List<ExerciseBean> scheduleBeansParam = request.getScheduleBean().getExercisesBean();
        requestBean = request;
        //Imposto valori della tabella
        ID.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        Name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        Description.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        Series.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumberSeries()));
        Reps.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumberReps()));
        RestTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRestTime().toString()));
        Check.setCellFactory(param -> createButtonCell());
    }

    @FXML
    public void send(MouseEvent event){
            requestBean.setReason(motivazione.getText());
            if(!requestModifyController.hasAlreadySentARequest(requestBean.getScheduleBean())){
                requestModifyController.sendRequest(requestBean);
                update();
                goToCustomerHomepage(event);
            }else{
                System.out.println("Hai già inviato una richiesta");
            }
    }


    @Override
    public void update() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invio richiesta");
        alert.setHeaderText(null);
        alert.setContentText("Richiesta inviata con successo");
        alert.showAndWait();
    }


}
