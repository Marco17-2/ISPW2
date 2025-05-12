package org.example.project3.query;

import org.example.project3.exceptions.DbOperationException;
import org.example.project3.model.Customer;

import java.sql.*;
import java.time.LocalDate;

public class RequestQuery {

    public static void sendRequest(Connection conn, String psychologist, String patient, LocalDate date) throws DbOperationException {
        String query = "INSERT INTO request (psychologist, patient, date) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, psychologist);
            preparedStatement.setString(2, patient);
            preparedStatement.setDate(3, Date.valueOf(date));
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

    public static ResultSet retrieveRequests(Connection conn, String mailTrainer) throws SQLException {
        //Mettere un order by date (da più a meno recente)
        String query = "SELECT customer.mail,customer.name, customer.surname, customer.gender, customer.online, customer.subscription, customer.injury, customer.startDate, customer.endDate, request.date " +
                "FROM customer JOIN request ON customer.mail = request.schedule.customer WHERE request.schedule.trainer = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, mailTrainer);
        return pstmt.executeQuery();
    }

    public static void deleteRequest(Connection conn, String mailCustomer, String mailTrainer, LocalDate date) throws DbOperationException {
        String query = "DELETE FROM request WHERE schedule.customer = ? AND schedule.trainer = ? AND date = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, mailCustomer);
            pstmt.setString(2, mailTrainer);
            pstmt.setDate(3, java.sql.Date.valueOf(date));
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
}
