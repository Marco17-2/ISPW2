package org.example.project3.utilities.others.mappers;

import org.example.project3.beans.ExerciseBean;
import org.example.project3.beans.ScheduleBean;
import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleMapper implements BeanAndModelMapper<ScheduleBean,Schedule> {
    CustomerMapper customerMapper = new CustomerMapper();
    TrainerMapper trainerMapper = new TrainerMapper();
    ExerciseMapper exerciseMapper = new ExerciseMapper();
    List<Exercise> exercises = new ArrayList<>();
    List<ExerciseBean> exerciseBeans = new ArrayList<>();
    Integer i=0;
    @Override
    public Schedule fromBeanToModel(ScheduleBean bean) {
        if(bean.getExercisesBean()==null) {
            return new Schedule(bean.getId(), bean.getName(), customerMapper.fromBeanToModel(bean.getCustomerBean()), trainerMapper.fromBeanToModel(bean.getTrainerBean()));
            //per ora è commentato perchè non so se effettivamente quando cercerò un scheda mi servirà la lista di tutti gli esercizi
            //exerciseMapper.fromBeanToModel(bean.getExercisesBean()));
            //mi da comunque problema perchè nel costruttore Schedule ho messo anche la lista di esercizi, dovrei rimuoverla anche da lì
        }else{
            exercises.clear();
            exerciseBeans.clear();
            exerciseBeans = bean.getExercisesBean();
            for(i=0;i<exerciseBeans.size();i++){
                exercises.add(exerciseMapper.fromBeanToModel(exerciseBeans.get(i)));
            }
            return new Schedule(bean.getId(), bean.getName(), customerMapper.fromBeanToModel(bean.getCustomerBean()), trainerMapper.fromBeanToModel(bean.getTrainerBean()),exercises);
        }

    }

    @Override
    public ScheduleBean fromModelToBean(Schedule model) {
        if(model.getExercises()==null) {
        return new ScheduleBean(model.getId(), model.getName(), customerMapper.fromModelToBean(model.getCustomer()), trainerMapper.fromModelToBean(model.getTrainer()));
                //per ora è commentato perchè non so se effettivamente quando cercerò un scheda mi servirà la lista di tutti gli esercizi
                //exerciseMapper.fromModelToBean(model.getExercises()));
    }else{
        exercises.clear();
        exerciseBeans.clear();
        exercises = model.getExercises();
        for(i=0;i<exercises.size();i++){
            exerciseBeans.add(exerciseMapper.fromModelToBean(exercises.get(i)));
        }
        return new ScheduleBean(model.getId(), model.getName(), customerMapper.fromModelToBean(model.getCustomer()), trainerMapper.fromModelToBean(model.getTrainer()),exerciseBeans);
    }
    }

}
