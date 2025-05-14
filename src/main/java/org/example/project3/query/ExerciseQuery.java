package org.example.project3.query;

import org.example.project3.exceptions.DbOperationException;
import org.example.project3.model.Customer;
import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;

import java.sql.*;
import java.time.LocalDate;

public class ExerciseQuery {
    private ExerciseQuery(){}

    public static void addExercise(Connection conn, Exercise exercise) throws DbOperationException {
        String query = "INSERT INTO exercise (name, description, numberseries, numberReps, restTime) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, exercise.getName());
            preparedStatement.setString(2, exercise.getDescription());
            preparedStatement.setInt(3, exercise.getNumberSeries());
            preparedStatement.setInt(4, exercise.getNumberReps());
            preparedStatement.setString(5, exercise.getRestTime().toString());
            preparedStatement.executeUpdate();
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
            pstmt.setString(3, exercise.getRestTime().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbOperationException("Errore nella modifica dell'esercizio", e);
        }
    }

    public static ResultSet retrieveExercise(Connection conn, String mailCustomer) throws SQLException {
        //Mettere un order by date (da più a meno recente)
        String query = "SELECT exercise.name, exercise.description, exercise.numberSeries, exercise.numberReps, exercise.restTime" +
                "FROM exercise JOIN partecipation ON exercise.id = partecipation.exercise JOIN schedule ON schedule.id = partecipation.schedule WHERE schedule.customer = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, mailCustomer);
        return pstmt.executeQuery();
    }

    public static void deleteExercise(Connection conn, Integer id) throws DbOperationException {
        String query = "DELETE FROM exercise WHERE exercise.id = ? ";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbOperationException("Errore nella rimozione dell'esercizio", e);
        }
    }

}
