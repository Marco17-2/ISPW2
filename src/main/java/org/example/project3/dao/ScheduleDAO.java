package org.example.project3.dao;

import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;

public interface ScheduleDAO {
    void addSchedule(Schedule schedule);
    void retrieveExercises(Schedule schedule);
    void retrieveSchedule(Schedule schedule);
    void deleteSchedule(Schedule schedule);
}
