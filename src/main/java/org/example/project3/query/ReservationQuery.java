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

    public void addRequest(Connection conn, Course course, Customer customer, LocalDate date, String hour) throws DbOperationException {
        String query = "INSERT INTO reservation(customer, course, date, hour) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, customer.getCredentials().getMail());
            preparedStatement.setString(2,course.getCourseName());
            preparedStatement.setString(3,date.toString());
            preparedStatement.setString(4,hour);

        } catch (SQLException e) {
            throw new DbOperationException("Errore aggiuntar richiesta", e);
        }
    }

    public void removeReservation(Connection conn, Course course, Customer customer, LocalDate date, String hour) throws DbOperationException {
        String query = "REMOVE FROM reservation WHERE customer = ? AND course = ? AND date = ? AND hour = ?";

        try(PreparedStatement pstmt= conn.prepareStatement(query)){
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, customer.getCredentials().getMail());
            pstmt.setString(3, date.toString());
            pstmt.setString(4, hour);

        }catch(SQLException e){
            throw new DbOperationException("Errore aggiuntar richiesta", e);
        }
    }
}
