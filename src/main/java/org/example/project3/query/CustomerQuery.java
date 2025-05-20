package org.example.project3.query;

import org.example.project3.exceptions.DbOperationException;
import org.example.project3.exceptions.MailAlreadyExistsException;
import org.example.project3.model.Customer;
import org.example.project3.model.LoggedUser;

import java.sql.*;
import java.time.LocalDate;

public class CustomerQuery {
    private CustomerQuery() {}

    public static void registerCustomer(Connection conn, Customer customer) throws SQLException, MailAlreadyExistsException, DbOperationException {
        String query = "INSERT INTO customer (mail, name, surname, gender, online, subscription, injury, startDate) VALUES (?,?,?,?,?,?,?,?)";
        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer.getCredentials().getMail());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getSurname());
            pstmt.setString(4, customer.getGender());
            pstmt.setBoolean(5, customer.isOnline());
            pstmt.setString(6, customer.getSubscription().getName());
            pstmt.setString(7, customer.getInjury());
            pstmt.setDate(8, Date.valueOf(LocalDate.now()));
            int rs = pstmt.executeUpdate();
            if (rs == 0) {
                throw new MailAlreadyExistsException("Mail già esistente");
            }
        }catch (SQLException e) {
            throw new DbOperationException("Errore nella registrazione", e);
        }
    }



    public static void modifyCustomer(Connection conn, Customer customer) throws DbOperationException {
        String query = "UPDATE customer SET name = ?, surname = ?, gender = ?, online = ? WHERE mail = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getSurname());
            pstmt.setString(3, customer.getGender());
            pstmt.setBoolean(4, customer.isOnline());
            pstmt.setString(5, customer.getCredentials().getMail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbOperationException("Errore nella modifica del profilo", e);
        }
    }

    public static ResultSet retrieveCustomer(Connection conn, String mail) throws SQLException {
        String query = "SELECT mail, name, surname, gender, online, subscription, injury, startDate, endDate FROM customer WHERE mail = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, mail);
        return pstmt.executeQuery();
    }

    public static void removeCustomer(Connection conn, String mail) throws DbOperationException {
        String deletePatient = "DELETE FROM customer WHERE mail = ?";
        String deleteUser = "DELETE FROM users WHERE mail = ?";

        try (PreparedStatement pstmt1 = conn.prepareStatement(deletePatient);
             PreparedStatement pstmt2 = conn.prepareStatement(deleteUser)) {

            pstmt1.setString(1, mail);
            pstmt1.executeUpdate();

            pstmt2.setString(1, mail);
            pstmt2.executeUpdate();

        } catch (SQLException e) {
            throw new DbOperationException("Errore nella rimozione del cliente", e);
        }
    }

}
