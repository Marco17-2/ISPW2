package org.example.project3.query;

import java.sql.*;
import java.time.LocalDate;

import org.example.project3.model.Customer;
import org.example.project3.model.Course;

import org.example.project3.exceptions.DbOperationException;

import org.example.project3.exceptions.MailAlreadyExistsException;

public class ReservationQuery {

    public static ResultSet retrieveReservation(Connection conn, String mail) throws SQLException {

        String query = "SELECT c.name, r.date, r.hour " +
                "FROM course c, reservation r" +
                " WHERE c.name = r.course AND r.email = ?";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, mail);
        return pstmt.executeQuery();
    }

    public static void addRequest(Connection conn, String course, String customer, String date, String hour) throws DbOperationException {
        String query = "INSERT INTO reservation(customer, course, date, hour) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, customer);
            preparedStatement.setString(2,course);
            preparedStatement.setString(3,date);
            preparedStatement.setString(4,hour);

        } catch (SQLException e) {
            throw new DbOperationException("Errore aggiuntar richiesta", e);
        }
    }

    public static void removeReservation(Connection conn, String course, String customer, String date, String hour) throws DbOperationException {
        String query = "REMOVE FROM reservation WHERE customer = ? AND course = ? AND date = ? AND hour = ?";

        try(PreparedStatement pstmt= conn.prepareStatement(query)){
            pstmt.setString(1, course);
            pstmt.setString(2, customer);
            pstmt.setString(3, date.toString());
            pstmt.setString(4, hour);

        }catch(SQLException e){
            throw new DbOperationException("Errore aggiuntar richiesta", e);
        }
    }
}
