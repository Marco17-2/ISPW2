package org.example.project3.dao;

import org.example.project3.model.Trainer;

public interface TrainerDAO {
    void registerTrainer(Trainer trainer);
    void retrieveTrainer(Trainer trainer);
    void removeTrainer(Trainer trainer);
    void modifyTrainer(Trainer trainer);
}
