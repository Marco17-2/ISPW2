package org.example.project3.dao;

import org.example.project3.exceptions.DAOException;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;

import java.util.List;

public interface ExerciseDAO {
    void addExerciseSchedule(Schedule schedule, Exercise exercise) throws DAOException;
    void addExercise(Exercise exercise) throws DAOException;
    void updateExercise(Exercise exercise) throws DAOException;
    void retrieveExercise(Exercise exercise) throws DAOException, NoResultException;
    void searchExercises(List<Exercise> exercises, String search, Schedule schedule) throws DAOException, NoResultException;
    void deleteExercise(Exercise exercise) throws DAOException;
}
