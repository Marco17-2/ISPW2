package org.example.project3.controller;

import org.example.project3.beans.CustomerBean;
import org.example.project3.beans.ExerciseBean;
import org.example.project3.beans.ScheduleBean;
import org.example.project3.dao.ExerciseDAO;
import org.example.project3.dao.ScheduleDAO;
import org.example.project3.model.Customer;
import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;
import org.example.project3.model.Trainer;
import org.example.project3.patterns.factory.BeanAndModelMapperFactory;
import org.example.project3.patterns.factory.FactoryDAO;
import org.example.project3.utilities.others.mappers.BeanAndModelMapper;

import java.util.ArrayList;
import java.util.List;

public class SearchController {
    private final BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final ScheduleDAO scheduleDAO;
    private final ExerciseDAO exerciseDAO;

    public SearchController(){
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
        this.scheduleDAO = FactoryDAO.getScheduleDAO();
        this.exerciseDAO = FactoryDAO.getExerciseDAO();
    }

    public void searchSchedules(List<ScheduleBean> scheduleBeans, String search){
        List<Schedule> schedules = new ArrayList<>();
        scheduleDAO.searchSchedules(schedules,search);
        for(Schedule schedule : schedules){
            ScheduleBean scheduleBean = beanAndModelMapperFactory.fromModelToBean(schedule,Schedule.class);
            scheduleBean.setId(schedule.getId());
            scheduleBean.setName(schedule.getName());
            scheduleBean.setCustomerBean(beanAndModelMapperFactory.fromModelToBean(schedule.getCustomer(), Customer.class));
            scheduleBean.setTrainerBean(beanAndModelMapperFactory.fromModelToBean(schedule.getTrainer(), Trainer.class));
        }
    }

    public void searchExercises(List<ExerciseBean> exerciseBeans, String search){
        List<Exercise> exercises = new ArrayList<>();
        exerciseDAO.searchExercises(exercises,search);
        for(Exercise exercise : exercises){
            ExerciseBean exerciseBean = beanAndModelMapperFactory.fromModelToBean(exercise, Exercise.class);
            exerciseBean.setId(exercise.getId());
            exerciseBean.setName(exercise.getName());
        }

    }
}
