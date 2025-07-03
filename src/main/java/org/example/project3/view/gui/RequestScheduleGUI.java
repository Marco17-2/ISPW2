package org.example.project3.view.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.project3.beans.CustomerBean;
import org.example.project3.beans.LoggedUserBean;
import org.example.project3.beans.RequestBean;
import org.example.project3.beans.ScheduleBean;
import org.example.project3.controller.RequestModifyController;
import org.example.project3.controller.ScheduleDetailsController;
import org.example.project3.controller.SearchController;
import org.example.project3.exceptions.LoadingException;
import org.example.project3.model.Request;
import org.example.project3.utilities.others.FXMLPathConfig;
import org.example.project3.utilities.others.mappers.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RequestScheduleGUI extends CommonGUI{

    public RequestScheduleGUI(FXMLPathConfig fxmlPathConfig, Session session) { super(session, fxmlPathConfig); }

    @FXML
    Button indietro;

    @FXML
    ImageView cancellaRicerca;

    @FXML
    TextField ricerca;

    @FXML
    Button cerca;

    @FXML
    TableView<ScheduleBean> ScheduleChoice;

    @FXML
    TableColumn<ScheduleBean, String> Name;

    @FXML
    TableColumn<ScheduleBean, String> Trainer;

    @FXML
    TableColumn<ScheduleBean, Void> Seleziona;

    private static List<ScheduleBean> originalSchedules = new ArrayList<>();

    private RequestModifyController requestModifyController= new RequestModifyController();
    private ScheduleDetailsController scheduleDetailsController= new ScheduleDetailsController();

    private TableCell<ScheduleBean, Void> createButtonCell(String buttonText) {
        return new TableCell<>() {
            private final Button button = createButton(buttonText);
            private Button createButton(String buttonText) {
                Button btn = new Button(buttonText);
                btn.setOnMouseClicked(event -> {
                    ScheduleBean scheduleBean = getTableView().getItems().get(getIndex());
                    scheduleDetailsController.retriveExercises(scheduleBean);
                    RequestBean requestBean= new RequestBean(scheduleBean);
                    completeRequest(requestBean, event);
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
    public void loadSchedule(List<ScheduleBean> scheduleBeansParam){
        if(originalSchedules.isEmpty()&&!scheduleBeansParam.isEmpty()){
            originalSchedules.addAll(scheduleBeansParam);
        }
        //Imposto valori della tabella
        Name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        Trainer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrainerBean().getCredentialsBean().getMail()));
        Seleziona.setCellFactory(param -> createButtonCell("Seleziona"));
        ScheduleChoice.getItems().clear();
        ScheduleChoice.getItems().addAll(scheduleBeansParam);

    }

    private void completeRequest(RequestBean requestBean, MouseEvent event){
        try {
            String fxmlPath = fxmlPathConfig.getFXMLPath(SCHEDULE_DETAILS);
            System.out.println("DEBUG: Percorso FXML recuperato: " + fxmlPath);
            URL resourceUrl = getClass().getResource(fxmlPath);
            System.out.println("DEBUG: URL risorsa ottenuta da getResource(): " + resourceUrl);
            FXMLLoader loader = new FXMLLoader(resourceUrl);
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(SCHEDULE_DETAILS)));
            loader.setControllerFactory(c -> new RequestExerciseGUI(fxmlPathConfig, session));
            Parent root = loader.load();
            ((RequestExerciseGUI)loader.getController()).loadExercises(requestBean);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new LoadingException("Errore durante il caricamento della scena", e);
        }
    }

    private SearchController searchController = new SearchController();

    @FXML
    private void cancelSearch(){
        loadSchedule(originalSchedules);
        ricerca.setText("");
        cancellaRicerca.setVisible(false);

    }

    @FXML
    private void SearchButton() {
        String searchText = ricerca.getText();
        cancellaRicerca.setVisible(true);

        if (searchText != null && !searchText.trim().isEmpty()) {
            // Il campo non è vuoto, esegui la ricerca
            List<ScheduleBean> schedulesTemp = new ArrayList<>();
            searchController.searchSchedules(schedulesTemp, searchText, (CustomerBean) session.getUser());
            loadSchedule(schedulesTemp);
        } else {
            loadSchedule(originalSchedules);
        }
    }

}
