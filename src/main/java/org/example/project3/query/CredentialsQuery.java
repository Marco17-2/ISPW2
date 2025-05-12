package org.example.project3.query;

import org.example.project3.exceptions.DbOperationException;
import org.example.project3.model.Credentials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredentialsQuery {
    private CredentialsQuery() {
    }

    public static int registerUser(Connection conn, Credentials credentialsBean) throws SQLException, DbOperationException {
        String query = "INSERT INTO users (mail, password, role) VALUES (?, ?, ?)";
        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, credentialsBean.getMail());
            pstmt.setString(2, credentialsBean.getPassword());
            pstmt.setString(3, credentialsBean.getRole().toString());
            return pstmt.executeUpdate(); //restituisce il numero di righe influenzate dalla query
        }catch (SQLException e) {
            throw new DbOperationException("Errore nella registrazione", e);
        }
    }

    public static ResultSet logQuery(Connection conn, Credentials credentialsBean) throws SQLException {
        String query = "SELECT mail, password, role FROM users WHERE mail = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, credentialsBean.getMail());
        pstmt.setString(2, credentialsBean.getPassword());
        return pstmt.executeQuery();
    }

    public static int checkMail(Connection conn, String mail) throws SQLException, DbOperationException {
        String query = "SELECT COUNT(*) FROM users WHERE mail = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, mail);
            ResultSet result = pstmt.executeQuery();
            result.next();
            return result.getInt(1);
        }catch (SQLException e) {
            throw new DbOperationException("Errore nella verifica della mail", e);
        }
    }

    public static void modifyCredentials(Connection conn, String mail, String password, String oldMail) throws DbOperationException {
        String query = "UPDATE users SET mail = ?, password = ? WHERE mail = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, mail);
            pstmt.setString(2, password);
            pstmt.setString(3, oldMail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbOperationException("Errore nella modifica delle credenziali", e);
        }
    }

}
