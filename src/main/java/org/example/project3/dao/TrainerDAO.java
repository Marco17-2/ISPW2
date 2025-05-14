package org.example.project3.dao;

import org.example.project3.exceptions.MailAlreadyExistsException;
import org.example.project3.model.Credentials;
import org.example.project3.model.Trainer;

public interface TrainerDAO {
    boolean emailExists(String email);
    boolean insertUser(Credentials credentials);
    void registerTrainer(Trainer trainer) throws MailAlreadyExistsException;
    void retrieveTrainer(Trainer trainer);
    void removeTrainer(Trainer trainer);
    void modifyTrainer(Trainer trainer);
}
