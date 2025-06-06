package org.example.project3.view.gui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.time.LocalDate;
import java.util.List;

import org.example.project3.exceptions.NoResultException;
import org.example.project3.utilities.others.mappers.Session;
import org.example.project3.utilities.others.FXMLPathConfig;
import org.example.project3.beans.*;

public class TrainerDetailGUI extends CommonGUI{

    protected TrainerDetailGUI(Session session, FXMLPathConfig fxmlPathConfig) {
        super(session, fxmlPathConfig);
    }

    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label mail;
    @FXML
    private Label gender;
    @FXML
    private Label specialization;
    @FXML
    private Button goback;

    public void TrainerDetail(CourseBean courseBean) throws NoResultException {

        //controller da implementare
        //TrainerBean trainer = trainerDescriptionController.trainerInfo(courseBean);

        printTrainerDetail(trainer);

    }

    public void printTrainerDetail(TrainerBean trainer) throws NoResultException {

        firstName.setText(trainer.getName());
        lastName.setText(trainer.getSurname());
        mail.setText(trainer.getCredentialsBean().getMail());
        gender.setText(trainer.getGender());

        List<String> specs = trainer.getSpecializations();
        if (specs != null && !specs.isEmpty()) {
            specialization.setText(String.join(", ", specs));
        } else {
            specialization.setText("Nessuna specializzazione");
        }
    }
}
