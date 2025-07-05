package org.example.project3.beans;

import java.util.List;

public class ScheduleBean {
    public long id;
    public String name;
    public CustomerBean customerBean;
    public TrainerBean trainerBean;
    public List<ExerciseBean> exercisesBean;

    public ScheduleBean(long id, String name, CustomerBean customerBean, TrainerBean trainerBean, List<ExerciseBean> exercisesBean) {
        this.id = id;
        this.name = name;
        this.customerBean = customerBean;
        this.trainerBean = trainerBean;
        this.exercisesBean = exercisesBean;
    }

    public ScheduleBean(long id, String name, CustomerBean customerBean, TrainerBean trainerBean) {
        this.id = id;
        this.name = name;
        this.customerBean = customerBean;
        this.trainerBean = trainerBean;
        this.exercisesBean = null;
    }

    public ScheduleBean(List<ExerciseBean> exercisesBean) {
        this.id = 0;
        this.name = null;
        this.customerBean = null;
        this.trainerBean = null;
        this.exercisesBean = exercisesBean;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CustomerBean getCustomerBean() {
        return customerBean;
    }

    public TrainerBean getTrainerBean() {
        return trainerBean;
    }

    public List<ExerciseBean> getExercisesBean() {
        return exercisesBean;
    }

     public void setId(long id) {
        this.id = id;
     }
    public void setName(String name) {
        this.name = name;
    }

    public void setCustomerBean(CustomerBean customerBean) {
        this.customerBean = customerBean;
    }

    public void setTrainerBean(TrainerBean trainerBean) {
        this.trainerBean = trainerBean;
    }

    public void setExercisesBean(List<ExerciseBean> exercisesBean) {
        this.exercisesBean = exercisesBean;
    }

    public void addExerciseBean(ExerciseBean exerciseBean) {
        this.exercisesBean.add(exerciseBean);
    }
}
