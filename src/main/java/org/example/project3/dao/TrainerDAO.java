package org.example.project3.dao;

import org.example.project3.exceptions.LoginAndRegistrationException;
import org.example.project3.exceptions.MailAlreadyExistsException;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.model.Credentials;
import org.example.project3.model.Trainer;
import org.example.project3.model.Course;

import java.sql.SQLException;
import java.util.List;

public interface TrainerDAO {
    boolean emailExists(String email);
    boolean insertUser(Credentials credentials);
    void registerTrainer(Trainer trainer) throws MailAlreadyExistsException, LoginAndRegistrationException;
    void retrieveTrainer(Trainer trainer) throws NoResultException;
    void removeTrainer(Trainer trainer);
    void modifyTrainer(Trainer trainer);
    void retrieveTrainerCourse(Course course, Trainer trainer);
    List<String> retrieveSpecializzation(Trainer trainer) throws SQLException;


}
