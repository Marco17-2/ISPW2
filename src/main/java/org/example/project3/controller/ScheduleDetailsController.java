package org.example.project3.controller;

import org.example.project3.beans.ExerciseBean;
import org.example.project3.beans.ScheduleBean;
import org.example.project3.dao.ScheduleDAO;
import org.example.project3.model.Customer;
import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;
import org.example.project3.model.Trainer;
import org.example.project3.patterns.factory.BeanAndModelMapperFactory;
import org.example.project3.patterns.factory.FactoryDAO;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDetailsController {
    private final BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final ScheduleDAO scheduleDAO;
    List<Exercise> exercises = new ArrayList<>();
    List<ExerciseBean> exerciseBeans = new ArrayList<>();
    Integer i=0;

    public ScheduleDetailsController(){
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
        this.scheduleDAO = FactoryDAO.getScheduleDAO();
    }

    public void retriveScheduleDetails(ScheduleBean scheduleBean){
        // converte il bean in model per poter cercare la scheda visto che non si effettua con il bean
        Schedule schedule = beanAndModelMapperFactory.fromBeanToModel(scheduleBean, ScheduleBean.class);
        scheduleDAO.retrieveSchedule(schedule);
        //inserisce i dati nel bean
        scheduleBean.setName(schedule.getName());
        scheduleBean.setCustomerBean(beanAndModelMapperFactory.fromModelToBean(schedule.getCustomer(), Customer.class));
        scheduleBean.setTrainerBean(beanAndModelMapperFactory.fromModelToBean(schedule.getTrainer(), Trainer.class));
    }

    public void retriveExercises(ScheduleBean scheduleBean){
        Schedule schedule = beanAndModelMapperFactory.fromBeanToModel(scheduleBean, ScheduleBean.class);
        scheduleDAO.retrieveSchedule(schedule);

        exercises.clear();
        exercises = schedule.getExercises();
        exerciseBeans.clear();
        for(i=0;i<exercises.size();i++){
            exerciseBeans.add(beanAndModelMapperFactory.fromModelToBean(exerciseBeans.get(i), ExerciseBean.class));
        }
        scheduleBean.setExercisesBean(exerciseBeans);
    }
}
