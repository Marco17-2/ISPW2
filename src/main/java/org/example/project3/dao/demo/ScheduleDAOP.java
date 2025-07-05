package org.example.project3.dao.demo;

import org.example.project3.dao.ScheduleDAO;
import org.example.project3.dao.demo.shared.SharedResources;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.model.*;
import org.example.project3.utilities.others.Printer;

import java.util.List;

public class ScheduleDAOP implements ScheduleDAO {
    @Override
    public void addSchedule(Schedule schedule) {
        SharedResources.getInstance().getSchedules().putIfAbsent(schedule.getId(), schedule);
    }

    @Override
    public void deleteSchedule(Schedule schedule) {
        SharedResources.getInstance().getSchedules().remove(schedule.getId());
    }

    @Override
    public void retrieveSchedule(Customer customer, List<Schedule> schedules) throws NoResultException {
        List<Schedule> storedSchedules = SharedResources.getInstance().getCustomerSchedules().get(customer.getCredentials().getMail());
        if (storedSchedules == null) {
            throw new NoResultException(schedules.getClass().getSimpleName() + " non trovato");
        } else if (storedSchedules != null){
            schedules.addAll(storedSchedules);
        }
    }

    @Override
    public void retrieveExercises(Schedule schedule) throws NoResultException {
        Schedule storedSchedule = SharedResources.getInstance().getSchedules().get(schedule.getId());
        if (storedSchedule == null) {
            throw new NoResultException(schedule.getClass().getSimpleName() + " non trovato");
        }
        List<Exercise> storedExercises = SharedResources.getInstance().getExerciseSchedules().get(storedSchedule.getId());
        for(Exercise exercise : storedExercises){
            schedule.addExercise(exercise);
        }
    }

    @Override
    public void searchSchedules(List<Schedule> schedules, String search, Customer user) {
        String lowerSearch = search.toLowerCase();
        Long id =null;
        for (Schedule schedule : SharedResources.getInstance().getSchedules().values()) {
            try{
                 id= Long.parseLong(lowerSearch);
            }catch(NumberFormatException e){
                Printer.errorPrint(""+e);}
            boolean match= (id!=null&&schedule.getId()==id);
            if (schedule.getCustomer().getCredentials().getMail().toLowerCase().contains(user.getCredentials().getMail().toLowerCase())&&
                    schedule.getTrainer().getCredentials().getMail().toLowerCase().contains(lowerSearch)||
                    match||
                    schedule.getName().toLowerCase().contains(lowerSearch)
            ) {
                schedules.add(schedule);
            }
        }

        if (schedules.isEmpty()) {
            throw new NoResultException("Nessuna scheda trovata per: " + search);
        }
    }
}
