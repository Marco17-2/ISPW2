package org.example.project3.controller;

import org.example.project3.beans.ExerciseBean;
import org.example.project3.dao.ExerciseDAO;
import org.example.project3.model.Exercise;
import org.example.project3.patterns.factory.BeanAndModelMapperFactory;
import org.example.project3.patterns.factory.FactoryDAO;
import org.example.project3.utilities.others.mappers.BeanAndModelMapper;

public class ExerciseDetailsController {
    private final BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final ExerciseDAO exerciseDAO;

    public ExerciseDetailsController(){
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
        this.exerciseDAO = FactoryDAO.getExerciseDAO();
    }

    public void retrieveExerciseDetails(ExerciseBean exerciseBean) {
        Exercise exercise = beanAndModelMapperFactory.fromBeanToModel(exerciseBean, ExerciseBean.class);
        exerciseDAO.retrieveExercise(exercise);
        exerciseBean.setId(exercise.getId());
        exerciseBean.setName(exercise.getName());
        exerciseBean.setDescription(exercise.getDescription());
        exerciseBean.setNumberSeries(exercise.getNumberSeries());
        exerciseBean.setNumberReps(exercise.getNumberReps());
        exerciseBean.setRestTime(exercise.getRestTime());
    }

}
