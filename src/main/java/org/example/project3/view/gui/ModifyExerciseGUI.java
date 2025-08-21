package org.example.project3.view.gui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.example.project3.beans.ExerciseBean;
import org.example.project3.beans.RequestBean;
import org.example.project3.beans.ScheduleBean;
import org.example.project3.controller.RequestModifyController;
import org.example.project3.controller.ScheduleController;
import org.example.project3.exceptions.DAOException;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.utilities.others.FXMLPathConfig;
import org.example.project3.utilities.others.mappers.Session;

import java.util.ArrayList;
import java.util.List;

public class ModifyExerciseGUI extends CommonGUI {
    public ModifyExerciseGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(session, fxmlPathConfig);
    }

    @FXML
    Button indietro;

    @FXML
    ImageView cancellaRicerca;

    @FXML
    TextField ricerca;

    @FXML
    Button cerca;

    @FXML
    TableView<ExerciseBean> exerciseChoice;

    @FXML
    TableColumn<ExerciseBean, Long> id;

    @FXML
    TableColumn<ExerciseBean, String> name;

    @FXML
    TableColumn<ExerciseBean, String> description;

    @FXML
    TableColumn<ExerciseBean, Integer> series;

    @FXML
    TableColumn<ExerciseBean, Integer> reps;

    @FXML
    TableColumn<ExerciseBean, String> restTime;

    @FXML
    TableColumn<ExerciseBean, Void> seleziona;

    @FXML
    Text error;


    private RequestModifyController requestModifyController = new RequestModifyController();
    private ScheduleController scheduleController = new ScheduleController();

    private RequestBean requestBean;

    private List<ExerciseBean> exerciseList = new ArrayList<>();

    private TableCell<ExerciseBean, Void> createButtonCell(String buttonText) {
        return new TableCell<>() {
            private final Button button = createButton(buttonText);

            private Button createButton(String buttonText) {
                Button btn = new Button(buttonText);
                btn.setOnMouseClicked(event -> {
                    try {
                        ExerciseBean exerciseBean = getTableView().getItems().get(getIndex());
                        scheduleController.updateSchedule(requestBean, exerciseBean);
                        requestModifyController.deleteRequest(requestBean);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Invio modifica");
                        alert.setHeaderText(null);
                        alert.setContentText("Richiesta modificata con successo");
                        alert.showAndWait();
                        goToTrainerHome(event);
                    } catch (DAOException _) {
                        error.setText("Errore nel DAO");
                        error.setVisible(true);
                    }
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
    public void loadExercises(RequestBean request, List<ExerciseBean>exerciseBeansParam) {
        try {
            error.setVisible(false);
            if (this.requestBean==null&&this.exerciseList.isEmpty()) {
                this.requestBean = request;
                this.exerciseList.addAll(exerciseBeansParam);
            }
            //Imposto valori della tabella
            id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
            name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            description.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
            series.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumberSeries()));
            reps.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumberReps()));
            restTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRestTime().getId() + " secondi"));
            seleziona.setCellFactory(param -> createButtonCell("Seleziona"));
            exerciseChoice.getItems().clear();
            exerciseChoice.getItems().addAll(exerciseBeansParam);
        } catch (NoResultException | DAOException e) {
            error.setText(e.getMessage());
            error.setVisible(true);
        }
    }

    @FXML
    public void cancelSearch() {
        requestBean.getScheduleBean().setExercisesBean(exerciseList);
        loadExercises(requestBean,exerciseList);
        ricerca.setText("");
        cancellaRicerca.setVisible(false);
    }

    @FXML
    private void searchButton() {
        String searchText = ricerca.getText();
        cancellaRicerca.setVisible(true);

        if (searchText != null && !searchText.trim().isEmpty()) {
            // Il campo non è vuoto, esegui la ricerca
            List<ExerciseBean> exercisesTemp = new ArrayList<>();
            try {
                scheduleController.searchAllExercises(exercisesTemp, searchText);
                RequestBean requestTemp = new RequestBean(new ScheduleBean(this.requestBean.getScheduleBean().getId(), exercisesTemp));
                loadExercises(requestTemp,exercisesTemp);
            } catch (NoResultException e) {
                error.setText(e.getMessage());
                error.setVisible(true);
            }

        } else {
            loadExercises(this.requestBean,exerciseList);
        }
    }



}
