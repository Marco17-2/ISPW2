package org.example.project3.dao;

import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;

public interface ExerciseDAO {
    void addExercise(Schedule schedule, Exercise exercise);
    void updateExercise(Exercise exercise);
    void retrieveExercise(Exercise exercise);
    void deleteExercise(Exercise exercise);
}
