package org.example.project3.dao.full.sql;

import org.example.project3.dao.TrainerDAO;
import org.example.project3.exceptions.DbOperationException;
import org.example.project3.exceptions.MailAlreadyExistsException;
import org.example.project3.model.Credentials;
import org.example.project3.model.Customer;
import org.example.project3.model.Subscription;
import org.example.project3.model.Trainer;
import org.example.project3.query.CredentialsQuery;
import org.example.project3.query.CustomerQuery;
import org.example.project3.query.TrainerQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainerDAOSQL implements TrainerDAO {

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String GENDER = "gender";
    private static final String ONLINE = "online";

    @Override
    public boolean emailExists(String mail)  {
        try (Connection conn = ConnectionSQL.getConnection()){
            int rs = CredentialsQuery.checkMail(conn, mail);
            if (rs != 0)
                return true;
        }catch (SQLException | DbOperationException e){
            handleException(e);

        }
        return false;
    }

    //inserisco l'utente (credenziali) nel database
    public boolean insertUser(Credentials credentials)  {
        try (Connection conn = ConnectionSQL.getConnection()) {
            int rs = CredentialsQuery.registerUser(conn, credentials);
            return rs != 0;
        }catch (SQLException |DbOperationException e){
            handleException(e);
            return false;
        }
    }

    public void registerTrainer(Trainer trainer) throws MailAlreadyExistsException {
        if(emailExists(trainer.getCredentials().getMail())) {
            throw new MailAlreadyExistsException(("Mail già registrata"));
        }//inserisco la password e l'email in user
        boolean flag = insertUser(trainer.getCredentials());
        if(flag){
            try (Connection conn = ConnectionSQL.getConnection()){
                TrainerQuery.registerTrainer(conn, trainer);
            }
            catch(SQLException | DbOperationException e){
                handleException(e);
            }
        }

    }

    @Override
    public void retrieveTrainer(Trainer trainer) {
        try (Connection conn = ConnectionSQL.getConnection();
             ResultSet rs = TrainerQuery.retrieveTrainer(conn, trainer.getCredentials().getMail())) {
            if (rs.next()) {
                trainer.setName(rs.getString(NAME));
                trainer.setSurname(rs.getString(SURNAME));
                trainer.setGender(rs.getString(GENDER));
                trainer.setOnline(rs.getBoolean(ONLINE));
            }
        } catch (SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void removeTrainer(Trainer trainer) {
        try (Connection conn = ConnectionSQL.getConnection()) {
            TrainerQuery.removeTrainer(conn, trainer.getCredentials().getMail());
        } catch (SQLException | DbOperationException e) {
            handleException(e);
        }
    }

    @Override
    public void modifyTrainer(Trainer trainer) {
        try(Connection conn = ConnectionSQL.getConnection()){
            TrainerQuery.modifyTrainer(conn, trainer);
        } catch(SQLException | DbOperationException e){
            handleException(e);
        }
    }

    private void handleException(Exception e) {
        System.out.println(String.format("%s", e.getMessage()));
    }
}
