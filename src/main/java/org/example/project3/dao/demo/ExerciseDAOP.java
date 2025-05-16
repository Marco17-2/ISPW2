package org.example.project3.dao.demo;

import org.example.project3.dao.ExerciseDAO;
import org.example.project3.dao.demo.shared.SharedResources;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.model.Customer;
import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDAOP implements ExerciseDAO {
    @Override
    public void addExercise(Schedule schedule, Exercise exercise) {
        SharedResources.getInstance().getExercises().putIfAbsent(exercise.getId(), exercise);
    }

    @Override
    public void deleteExercise(Exercise exercise) {
        SharedResources.getInstance().getCustomers().remove(exercise.getId());
    }

    @Override
    public void retrieveExercise(Exercise exercise) throws NoResultException {
        Exercise storedExercise = SharedResources.getInstance().getExercises().get(exercise.getId());
        if (storedExercise == null) {
            throw new NoResultException(exercise.getClass().getSimpleName() + " non trovato");
        }
        exercise.setName(storedExercise.getName());
        exercise.setDescription(storedExercise.getDescription());
        exercise.setNumberSeries(storedExercise.getNumberSeries());
        exercise.setNumberReps(storedExercise.getNumberReps());
        exercise.setRestTime(storedExercise.getRestTime());
    }

    @Override
    public void updateExercise(Exercise exercise) {
        SharedResources.getInstance().getExercises().put(exercise.getId(), exercise);
    }
}
