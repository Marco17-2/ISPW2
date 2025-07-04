package org.example.project3.controller;

import org.example.project3.beans.CustomerBean;
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
    List<Schedule> schedules = new ArrayList<>();

    public ScheduleDetailsController(){
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
        this.scheduleDAO = FactoryDAO.getScheduleDAO();
    }


    public void retriveScheduleDetails(CustomerBean customerBean, List<ScheduleBean> scheduleBeans){
        // converte il bean in model per poter cercare la scheda visto che non si effettua con il bean
        List<Schedule> schedules = new ArrayList<>();

        Customer customer = beanAndModelMapperFactory.fromBeanToModel(customerBean, CustomerBean.class);
        scheduleDAO.retrieveSchedule(customer, schedules);
        for(int i = 0; i < schedules.size(); i++){
            ScheduleBean scheduleBean = beanAndModelMapperFactory.fromModelToBean(schedules.get(i), Schedule.class);

            //inserisce i dati nel bean
            scheduleBean.setId(schedules.get(i).getId());
            scheduleBean.setName(schedules.get(i).getName());
            scheduleBean.setCustomerBean(beanAndModelMapperFactory.fromModelToBean(schedules.get(i).getCustomer(), Customer.class));
            scheduleBean.setTrainerBean(beanAndModelMapperFactory.fromModelToBean(schedules.get(i).getTrainer(), Trainer.class));

            scheduleBeans.add(scheduleBean);
        }
    }

    public void retriveExercises(ScheduleBean scheduleBean){
        Schedule schedule = beanAndModelMapperFactory.fromBeanToModel(scheduleBean, ScheduleBean.class);
        scheduleDAO.retrieveExercises(schedule);
        exercises.clear();
        exercises = schedule.getExercises();
        exerciseBeans.clear();
        for(int i=0;i<exercises.size();i++){
            exerciseBeans.add(beanAndModelMapperFactory.fromModelToBean(exercises.get(i), Exercise.class));
        }
        scheduleBean.setExercisesBean(exerciseBeans);
    }
}
