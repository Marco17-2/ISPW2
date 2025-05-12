package org.example.project3.dao;

import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;

public interface ExerciseDAO {
    void addExercise(Schedule schedule, Exercise exercise);
    void updateExercise(Schedule schedule, Exercise exercise);
    void deleteExercise(Schedule schedule, Exercise exercise);
}
