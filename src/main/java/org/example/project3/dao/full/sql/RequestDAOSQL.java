package org.example.project3.dao.full.sql;

import org.example.project3.dao.RequestDAO;
import org.example.project3.exceptions.DAOException;
import org.example.project3.exceptions.DbOperationException;
import org.example.project3.model.*;
import org.example.project3.query.RequestQuery;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RequestDAOSQL implements RequestDAO {
    private static final String ID = "id";
    private static final String SCHEDULE = "schedule";
    private static final String EXERCISE = "exercise";
    private static final String REASON = "reason";
    private static final String CUSTOMER = "customer";
    private static final String DATETIME = "datetime";
    private static final String COURSE = "course";

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String GENDER = "gender";
    private static final String EMAIL = "email";


    @Override
    public void sendRequest(Request request) {
        try(Connection conn = ConnectionSQL.getConnection()) {
            RequestQuery.sendRequest(conn, request.getSchedule().getCustomer().getCredentials().getMail(), request.getSchedule().getTrainer().getCredentials().getMail(), request.getDateTime());
        } catch (SQLException | DbOperationException e){
            handleException(e);

        }
    }

    @Override
    public boolean hasAlreadySentRequest(Request request) {
        try (Connection conn = ConnectionSQL.getConnection()) {
            ResultSet rs = RequestQuery.hasAlreadySentARequest(conn, request.getSchedule().getCustomer().getCredentials().getMail(), request.getSchedule().getTrainer().getCredentials().getMail());
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException | DbOperationException e) {
            handleException(e);
            return false;
        }
    }

//    @Override
//    public void retrieveCustomerRequest(Customer customer, List<Request> requests) {
//        try(Connection conn = ConnectionSQL.getConnection()){
//            ResultSet rs = RequestQuery.retrieveRequests(conn, customer.getCredentials().getMail());
//            while(rs.next()){
//                Request request = new Request(
//                        rs.getInt(ID),
//                        rs.getInt(SCHEDULE),
//                        rs.getInt(EXERCISE),
//                        rs.getString(REASON),
//                        customer,
//                        rs.getObject(DATETIME),
//                        null
//                );
//                requests.add(request);
//            }
//
//        }catch (SQLException e){
//            throw new DAOException("Errore nel recupero delle richieste", e);
//        }
//    }

    @Override
    public void deleteRequest(Request request) {
        try(Connection conn = ConnectionSQL.getConnection()){
            RequestQuery.deleteRequest(conn, request.getSchedule().getCustomer().getCredentials().getMail(), request.getSchedule().getTrainer().getCredentials().getMail(), request.getDateTime());
        } catch(SQLException | DbOperationException e){
            handleException(e);
        }
    }

    @Override
    public void retrieveCourseRequest(Trainer trainer, List<Reservation> reservationList){

        try(Connection conn = ConnectionSQL.getConnection()){
            ResultSet rs = RequestQuery.retireveCourseRequest(conn, trainer.getCredentials().getMail());
            while(rs.next()){

                // finire
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleException(Exception e) {
        System.out.println(String.format("%s", e.getMessage()));
    }
}
