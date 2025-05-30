package org.example.project3.dao.demo;

import org.example.project3.dao.ScheduleDAO;
import org.example.project3.dao.demo.shared.SharedResources;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.model.Customer;
import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;
import org.example.project3.model.Subscription;

import java.util.ArrayList;
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
        schedule.setExercises(storedSchedule.getExercises());
    }

    @Override
    public void searchSchedules(List<Schedule> schedules, String search) {
        String lowerSearch = search.toLowerCase();

        for (Schedule schedule : SharedResources.getInstance().getSchedules().values()) {
            if (schedule.getCustomer().getCredentials().getMail().toLowerCase().contains(lowerSearch)||
                    schedule.getTrainer().getCredentials().getMail().toLowerCase().contains(lowerSearch)||
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
