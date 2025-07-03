package org.example.project3.dao;

import org.example.project3.exceptions.NoResultException;
import org.example.project3.model.Customer;
import org.example.project3.model.Exercise;
import org.example.project3.model.LoggedUser;
import org.example.project3.model.Schedule;

import java.util.List;

public interface ScheduleDAO {
    void addSchedule(Schedule schedule);
    void retrieveExercises(Schedule schedule);
    void retrieveSchedule(Customer customer, List<Schedule> schedules);
    void searchSchedules(List<Schedule> schedules, String search, Customer user);
    void deleteSchedule(Schedule schedule);
}
