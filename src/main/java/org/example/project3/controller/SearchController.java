package org.example.project3.controller;

import org.example.project3.beans.CustomerBean;
import org.example.project3.beans.ExerciseBean;
import org.example.project3.beans.ScheduleBean;
import org.example.project3.dao.ExerciseDAO;
import org.example.project3.dao.ScheduleDAO;
import org.example.project3.model.*;
import org.example.project3.patterns.factory.BeanAndModelMapperFactory;
import org.example.project3.patterns.factory.FactoryDAO;


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

    public void searchSchedules(List<ScheduleBean> scheduleBeans, String search, CustomerBean userBean){
        scheduleBeans.clear();
        List<Schedule> schedules = new ArrayList<>();
        Customer user = beanAndModelMapperFactory.fromBeanToModel(userBean, CustomerBean.class);
        scheduleDAO.searchSchedules(schedules,search,user);
        for(Schedule schedule : schedules){
            ScheduleBean scheduleBean = beanAndModelMapperFactory.fromModelToBean(schedule,Schedule.class);
            scheduleBean.setName(schedule.getName());
            scheduleBean.setTrainerBean(beanAndModelMapperFactory.fromModelToBean(schedule.getTrainer(), Trainer.class));
            scheduleBeans.add(scheduleBean);
        }
    }

    public void searchExercises(List<ExerciseBean> exerciseBeans, String search, ScheduleBean scheduleBean){
        exerciseBeans.clear();
        List<Exercise> exercises = new ArrayList<>();
        Schedule schedule = beanAndModelMapperFactory.fromBeanToModel(scheduleBean, ScheduleBean.class);
        exerciseDAO.searchExercises(exercises,search,schedule);
        for(Exercise exercise : exercises){
            ExerciseBean exerciseBean = beanAndModelMapperFactory.fromModelToBean(exercise, Exercise.class);
            exerciseBean.setName(exercise.getName());
            exerciseBeans.add(exerciseBean);
        }

    }
}
