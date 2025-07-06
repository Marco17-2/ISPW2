package org.example.project3.dao;

import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;

import java.util.List;

public interface ExerciseDAO {
    void addExerciseSchedule(Schedule schedule, Exercise exercise);
    void addExercise(Exercise exercise);
    void updateExercise(Exercise exercise);
    void retrieveExercise(Exercise exercise);
    void searchExercises(List<Exercise> exercises, String search, Schedule schedule);
    void deleteExercise(Exercise exercise);
}
