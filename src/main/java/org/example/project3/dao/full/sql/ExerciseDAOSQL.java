package org.example.project3.dao.full.sql;

import org.example.project3.dao.ExerciseDAO;
import org.example.project3.exceptions.DbOperationException;
import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;
import org.example.project3.model.Subscription;
import org.example.project3.query.ExerciseQuery;
import org.example.project3.query.SubscriptionQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Period;

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
                exercise.setRestTime(rs.getObject(RESTTIME, Duration.class));
            }
        } catch (SQLException e) {
            handleException(e);
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
        System.out.println(String.format("%s", e.getMessage()));
    }
}
