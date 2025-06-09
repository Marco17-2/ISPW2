package org.example.project3.controller;

import org.example.project3.dao.TrainerDAO;
import org.example.project3.patterns.factory.FactoryDAO;
import org.example.project3.patterns.factory.BeanAndModelMapperFactory;

import org.example.project3.beans.*;
import org.example.project3.model.*;


public class TrainerDescriptionController {

    private final BeanAndModelMapperFactory factory;
    private final TrainerDAO trainerDAO;

    public TrainerDescriptionController() {
        this.factory = BeanAndModelMapperFactory.getInstance();
        this.trainerDAO = FactoryDAO.getTrainerDAO();
    }

    public TrainerBean trainerDescription(CourseBean courseBean){
        Trainer trainer;
        // finire
        // conversione trainer da dao per la GUI;
        return null;
    }

}
