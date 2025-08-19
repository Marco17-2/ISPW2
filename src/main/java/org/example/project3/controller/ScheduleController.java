package org.example.project3.controller;

import org.example.project3.beans.CustomerBean;
import org.example.project3.beans.ExerciseBean;
import org.example.project3.beans.ScheduleBean;
import org.example.project3.dao.ExerciseDAO;
import org.example.project3.dao.ScheduleDAO;
import org.example.project3.exceptions.DAOException;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.model.Customer;
import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;
import org.example.project3.model.Trainer;
import org.example.project3.patterns.factory.BeanAndModelMapperFactory;
import org.example.project3.patterns.factory.FactoryDAO;

import java.util.ArrayList;
import java.util.List;

public class ScheduleController {
    private final BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final ScheduleDAO scheduleDAO;
    private final ExerciseDAO exerciseDAO;
    List<Exercise> exercises = new ArrayList<>();
    List<ExerciseBean> exerciseBeans = new ArrayList<>();

    public ScheduleController(){
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
        this.scheduleDAO = FactoryDAO.getScheduleDAO();
        this.exerciseDAO = FactoryDAO.getExerciseDAO();
    }

    public void retriveScheduleDetails(CustomerBean customerBean, List<ScheduleBean> scheduleBeans)throws NoResultException, DAOException{
        // converte il bean in model per poter cercare la scheda visto che non si effettua con il bean
        try{

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
    }catch(NoResultException _){
            throw new NoResultException("Nessuna scheda trovata");
        }catch(DAOException e){
            throw new DAOException("Errore durante la ricerca",e);
        }
    }

    public void retriveExercises(ScheduleBean scheduleBean)throws NoResultException,DAOException{
        try {
            Schedule schedule = beanAndModelMapperFactory.fromBeanToModel(scheduleBean, ScheduleBean.class);
            scheduleDAO.retrieveExercises(schedule);
            exercises.clear();
            exercises = schedule.getExercises();
            exerciseBeans.clear();
            for (int i = 0; i < exercises.size(); i++) {
                exerciseBeans.add(beanAndModelMapperFactory.fromModelToBean(exercises.get(i), Exercise.class));
            }
            scheduleBean.setExercisesBean(exerciseBeans);
        }catch(NoResultException _){
            throw new NoResultException("Nessuna esercizio trovato per la scheda" + scheduleBean.getId());
        }catch(DAOException e){
            throw new DAOException("Errore durante la ricerca",e);
        }
    }

    public void retrieveExerciseDetails(ExerciseBean exerciseBean) throws NoResultException,DAOException{
        try {
            Exercise exercise = beanAndModelMapperFactory.fromBeanToModel(exerciseBean, ExerciseBean.class);
            exerciseDAO.retrieveExercise(exercise);
            exerciseBean.setName(exercise.getName());
            exerciseBean.setDescription(exercise.getDescription());
            exerciseBean.setNumberSeries(exercise.getNumberSeries());
            exerciseBean.setNumberReps(exercise.getNumberReps());
            exerciseBean.setRestTime(exercise.getRestTime());
        }catch(NoResultException _){
            throw new NoResultException("Nessun esercizio trovato");
        }catch(DAOException e){
            throw new DAOException("Errore durante la ricerca",e);
        }
    }

    public void searchSchedules(List<ScheduleBean> scheduleBeans, String search, CustomerBean userBean) throws NoResultException,DAOException{
        try {
            scheduleBeans.clear();
            List<Schedule> schedules = new ArrayList<>();
            Customer user = beanAndModelMapperFactory.fromBeanToModel(userBean, CustomerBean.class);
            scheduleDAO.searchSchedules(schedules, search, user);
            for (Schedule schedule : schedules) {
                ScheduleBean scheduleBean = beanAndModelMapperFactory.fromModelToBean(schedule, Schedule.class);
                scheduleBean.setName(schedule.getName());
                scheduleBean.setTrainerBean(beanAndModelMapperFactory.fromModelToBean(schedule.getTrainer(), Trainer.class));
                scheduleBeans.add(scheduleBean);
            }
        }catch(NoResultException _){
            throw new NoResultException("Non è stata trovata nessuna scheda");
        }catch(DAOException e){
            throw new DAOException("Errore nella ricerca della scheda", e);
        }
    }

    public void searchExercises(List<ExerciseBean> exerciseBeans, String search, ScheduleBean scheduleBean) throws NoResultException,DAOException{
        try {
            exerciseBeans.clear();
            List<Exercise> exercises = new ArrayList<>();
            Schedule schedule = beanAndModelMapperFactory.fromBeanToModel(scheduleBean, ScheduleBean.class);
            exerciseDAO.searchExercises(exercises, search, schedule);
            for (Exercise exercise : exercises) {
                ExerciseBean exerciseBean = beanAndModelMapperFactory.fromModelToBean(exercise, Exercise.class);
                exerciseBean.setName(exercise.getName());
                exerciseBeans.add(exerciseBean);
            }
        }catch(NoResultException _){
            throw new NoResultException("Non è stato trovato nessun esercizio");
        }catch(DAOException e){
            throw new DAOException("Errore nella ricerca della scheda", e);
        }

    }

}
