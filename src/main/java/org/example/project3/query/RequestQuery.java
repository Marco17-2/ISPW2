package org.example.project3.query;

import org.example.project3.exceptions.DbOperationException;
import org.example.project3.model.Customer;
import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;
import org.example.project3.model.Course;

import java.sql.*;
import java.time.LocalDateTime;

public class RequestQuery {

    public static void sendRequest(Connection conn, Schedule schedule, Exercise exercise, String reason) throws DbOperationException {
        String query = "INSERT INTO request (schedule, exercise, reason, datetime) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setLong(1, schedule.getId());
            preparedStatement.setLong(2, exercise.getId());
            preparedStatement.setString(3, reason);
            preparedStatement.setObject(4, LocalDateTime.now());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbOperationException("Errore nell'invio della richiesta db: " + e.getMessage(), e);
        }
    }

    public static ResultSet hasAlreadySentARequest(Connection conn, Schedule schedule) throws DbOperationException {
        String query = "SELECT COUNT(*) FROM request WHERE schedule = ?";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setLong(1, schedule.getId());
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DbOperationException("Errore nel controllo della richiesta", e);
        }
    }

    public static ResultSet retrieveRequests(Connection conn, String mailCustomer) throws SQLException {
        //Mettere un order by date (da più a meno recente)
        String query = "SELECT id, schedule, exercise, reason, date FROM request WHERE schedule.customer = ?";
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

    public static ResultSet retireveCourseRequest(Connection conn, String trainer) throws SQLException {

        String query = "SELECT cu.mail, cu.name, cu.surname, cu.gender, cu.injury, cu.birthday, r.date, r.hour, c.name, c.id FROM customer cu JOIN pending r ON r.customer = cu.mail JOIN course c ON c.id = r.course JOIN trainer t ON t.email=c.trainer WHERE t.email = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, trainer);
        return pstmt.executeQuery();
    }

    public static void removeCourseRequest(Connection conn, String customer, Integer course, String date, String hour) throws SQLException, DbOperationException {
        String query = "DELETE FROM pending r WHERE r.customer = ? AND r.course = ? AND r.date = ? AND r.hour = ?";

        try( PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer);
            pstmt.setInt(2, course);
            pstmt.setString(3, date);
            pstmt.setString(4, hour);


            pstmt.executeUpdate();
        }catch(SQLException e) {
            //da rimuovere
            System.out.println("Errore nell'invio della richiesta db: " + e.getMessage());

            throw new DbOperationException("Errore nella rimozione delle richiesta", e);
        }
    }

    public static void addCourseRequest(Connection conn,  String customer, Integer course, String date, String hour) throws SQLException, DbOperationException {
        String query = "INSERT INTO pending(customer,course,date,hour) VALUES (?, ?, ?, ?)";

        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer);
            pstmt.setInt(2, course);
            pstmt.setObject(3, date);
            pstmt.setString(4, hour);

            pstmt.executeUpdate();
        }catch (SQLException e) {
            throw new DbOperationException("Errore invio richieste"+e.getMessage(), e);
        }

    }


}
