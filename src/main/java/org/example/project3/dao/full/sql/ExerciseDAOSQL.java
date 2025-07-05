package org.example.project3.dao.full.sql;

import org.example.project3.dao.ExerciseDAO;
import org.example.project3.exceptions.DAOException;
import org.example.project3.exceptions.DbOperationException;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.model.*;
import org.example.project3.query.ExerciseQuery;
import org.example.project3.utilities.enums.RestTime;
import org.example.project3.utilities.others.Printer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ExerciseDAOSQL implements ExerciseDAO {
    private static final String ID="id";
    private static final String NAME="name";
    private static final String DESCRIPTION="description";
    private static final String NUMBERSERIES="numberSeries";
    private static final String NUMBERREPS="numberReps";
    private static final String RESTTIME="restTime";

    @Override
    public void addExercise(Schedule schedule, Exercise exercise){
        try (Connection conn = ConnectionSQL.getConnection()) {
            ExerciseQuery.addExercise(conn, schedule, exercise);
        } catch (SQLException | DbOperationException e) {
            handleException(e);
        }
    }

    @Override
    public void retrieveExercise(Exercise exercise) {
        try (Connection conn = ConnectionSQL.getConnection();
             ResultSet rs = ExerciseQuery.retrieveExercise(conn, exercise)){
            if (rs.next()) {
                exercise.setName(rs.getString(NAME));
                exercise.setDescription(rs.getString(DESCRIPTION));
                exercise.setNumberSeries(rs.getInt(NUMBERSERIES));
                exercise.setNumberReps(rs.getInt(NUMBERREPS));
                exercise.setRestTime(RestTime.convertIntToRestTime(rs.getInt(RESTTIME)));
            }
        } catch (SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void searchExercises(List<Exercise> exercises, String search, Schedule schedule) {
        try (Connection conn = ConnectionSQL.getConnection()) {
            ResultSet rs = ExerciseQuery.searchExercises(conn, search, schedule);
            while (rs.next()) {
                Exercise exercise = new Exercise(
                        rs.getInt(ID),
                        rs.getString(NAME),
                        rs.getString(DESCRIPTION),
                        rs.getInt(NUMBERSERIES),
                        rs.getInt(NUMBERREPS),
                        RestTime.convertIntToRestTime(rs.getInt(RESTTIME))
                );
                exercises.add(exercise);
            }
        } catch (SQLException | NoResultException e) {
            throw new DAOException("Errore nella ricerca della scheda", e);
        }
    }

    @Override
    public void deleteExercise(Exercise exercise) {
        try (Connection conn = ConnectionSQL.getConnection()) {
            ExerciseQuery.deleteExercise(conn, exercise.getId());
        } catch (SQLException | DbOperationException e) {
            handleException(e);
        }
    }

    @Override
    public void updateExercise( Exercise exercise) {
        try(Connection conn = ConnectionSQL.getConnection()){
            ExerciseQuery.modifyExercise(conn, exercise);
        } catch(SQLException | DbOperationException e){
            handleException(e);
        }
    }

    private void handleException(Exception e) {
        Printer.println(String.format("%s", e.getMessage()));
    }
}
