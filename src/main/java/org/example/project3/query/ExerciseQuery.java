package org.example.project3.query;

import org.example.project3.exceptions.DbOperationException;
import org.example.project3.model.Customer;
import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;
import org.example.project3.utilities.others.Printer;

import java.sql.*;

public class ExerciseQuery {
    private ExerciseQuery(){}

    public static void addExercise(Connection conn, Schedule schedule, Exercise exercise) throws DbOperationException {
        String insertExercise = "INSERT INTO exercise (name, description, numberseries, numberReps, restTime) VALUES (?, ?, ?, ?, ?)";
        String intoSchedule = "INSERT INTO participation (schedule, exercise) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertExercise);
             PreparedStatement pstmt = conn.prepareStatement(intoSchedule)) {

            preparedStatement.setString(1, exercise.getName());
            preparedStatement.setString(2, exercise.getDescription());
            preparedStatement.setInt(3, exercise.getNumberSeries());
            preparedStatement.setInt(4, exercise.getNumberReps());
            preparedStatement.setInt(5, exercise.getRestTime().getId());
            preparedStatement.executeUpdate();


            pstmt.setLong(1, schedule.getId());
            pstmt.setLong(2, exercise.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DbOperationException("Errore nell'aggiunta dell'esercizio", e);
        }
    }

    public static void modifyExercise(Connection conn, Exercise exercise) throws DbOperationException {
        String query = "UPDATE exercise SET numberSeries = ?, numberReps = ?, restTime = ? WHERE id = ? ";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, exercise.getNumberSeries());
            pstmt.setInt(2, exercise.getNumberReps());
            pstmt.setInt(3, exercise.getRestTime().getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbOperationException("Errore nella modifica dell'esercizio", e);
        }
    }

    public static ResultSet retrieveExercise(Connection conn, Exercise exercise) throws SQLException {
        //Mettere un order by date (da più a meno recente)
        String query = "SELECT name, description, numberSeries, numberReps, restTime FROM exercise WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setLong(1, exercise.getId());
        return pstmt.executeQuery();
    }

    public static ResultSet searchExercises(Connection conn, String search, Schedule schedule) throws SQLException {
        try {
            String query = "SELECT exercise.id, exercise.name, exercise.description, exercise.numberSeries, exercise.numberReps, exercise.restTime FROM exercise JOIN participation ON participation.exercise = exercise.id JOIN schedule ON schedule.id = participation.schedule WHERE LOWER(exercise.name) LIKE LOWER(?) AND schedule.customer = LOWER(?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            String wildcard = "%" + search + "%";
            pstmt.setString(1, wildcard);
            pstmt.setString(2, schedule.getCustomer().getCredentials().getMail());
            return pstmt.executeQuery();
        } catch (SQLException e) {
            Printer.errorPrint("Errore nella ricerca della scheda");
            return null;
        }
    }

    public static void deleteExercise(Connection conn, Long id) throws DbOperationException {
        String query = "DELETE FROM exercise WHERE id = ? ";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbOperationException("Errore nella rimozione dell'esercizio", e);
        }
    }

}
