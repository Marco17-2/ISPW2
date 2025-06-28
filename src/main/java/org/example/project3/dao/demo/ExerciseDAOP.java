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
        if (schedule == null) {
            throw new NoResultException(exercise.getClass().getSimpleName() + " non trovato");
        }
        schedule.addExercise(exercise);
        SharedResources.getInstance().getSchedules().put(schedule.getId(), schedule);
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
    public void searchExercises(List<Exercise> exercises, String search, Schedule schedule) {
        String lowerSearch = search.toLowerCase();

        Schedule storedSchedule = SharedResources.getInstance().getSchedules().get(schedule.getId());
//        if (storedSchedule == null) {
//            throw new NoResultException(schedule.getClass().getSimpleName() + " non trovato");
//        }
        schedule.setExercises(storedSchedule.getExercises());

        for (Exercise exercise : schedule.getExercises()) {
            if (exercise.getName().toLowerCase().contains(lowerSearch)) {
                exercises.add(exercise);
            }
        }

        if (exercises.isEmpty()) {
            throw new NoResultException("Nessun esercizio trovato per: " + search);
        }
    }

    @Override
    public void updateExercise(Exercise exercise) {
        SharedResources.getInstance().getExercises().put(exercise.getId(), exercise);
    }
}
