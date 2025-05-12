package org.example.project3.dao;

import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;

public interface ExAndScheduleDAO {
    //metodi per aggiungere, modificare e rimuovere schede e esercizi
    void addExercise(Schedule schedule, Exercise exercise);
    void updateExercise(Schedule schedule, Exercise exercise);
    void deleteExercise(Schedule schedule, Exercise exercise);
    void addSchedule(Schedule schedule);
    void updateSchedule(Schedule schedule);
    void deleteSchedule(Schedule schedule);
}
