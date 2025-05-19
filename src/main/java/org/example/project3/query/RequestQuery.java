package org.example.project3.query;

import org.example.project3.exceptions.DbOperationException;
import org.example.project3.model.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RequestQuery {

    public static void sendRequest(Connection conn, String psychologist, String patient, LocalDateTime date) throws DbOperationException {
        String query = "INSERT INTO request (psychologist, patient, date) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, psychologist);
            preparedStatement.setString(2, patient);
            preparedStatement.setObject(3, date);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbOperationException("Errore nell'invio della richiesta", e);
        }
    }

    public static ResultSet hasAlreadySentARequest(Connection conn, String trainer, String customer) throws DbOperationException {
        String query = "SELECT COUNT(*) FROM request WHERE psychologist = ? AND patient = ?";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, trainer);
            preparedStatement.setString(2, customer);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DbOperationException("Errore nel controllo della richiesta", e);
        }
    }

    public static ResultSet retrieveRequests(Connection conn, String mailCustomer) throws SQLException {
        //Mettere un order by date (da più a meno recente)
        String query = "SELECT request.id, request.schedule, request.exercise, request.reason, request.date FROM request WHERE request.schedule.customer = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, mailCustomer);
        return pstmt.executeQuery();
    }

    public static void deleteRequest(Connection conn, String mailCustomer, String mailTrainer, LocalDateTime date) throws DbOperationException {
        String query = "DELETE FROM request WHERE schedule.customer = ? AND schedule.trainer = ? AND date = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, mailCustomer);
            pstmt.setString(2, mailTrainer);
            pstmt.setObject(3, date);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbOperationException("Errore nella rimozione della richiesta", e);
        }
    }

    public static void deleteOtherRequests(Connection conn, Customer customer) throws DbOperationException {
        String query = "DELETE FROM request WHERE schedule.customer = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer.getCredentials().getMail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbOperationException("Errore nella rimozione delle altre richieste", e);
        }
    }

    public static ResultSet retireveCourseRequest(Connection conn, String course) throws SQLException {

        String query = "SELECT cu.mail, cu.name, cu.surname, cu.gender, r.data" +
                "FROM customer cu, courseRequest r " +
                "WHERE r.cliente = cu.mail AND r.course = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, course);
        return pstmt.executeQuery();
    }


}
