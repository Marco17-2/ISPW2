package org.example.project3.dao;

import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;

import java.util.List;

public interface ExerciseDAO {
    void addExercise(Schedule schedule, Exercise exercise);
    void updateExercise(Exercise exercise);
    void retrieveExercise(Exercise exercise);
    void searchExercises(List<Exercise> exercises, String search);
    void deleteExercise(Exercise exercise);
}
