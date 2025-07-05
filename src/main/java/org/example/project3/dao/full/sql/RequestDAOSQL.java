package org.example.project3.dao.full.sql;

import org.example.project3.dao.RequestDAO;
import org.example.project3.exceptions.DAOException;
import org.example.project3.exceptions.DbOperationException;
import org.example.project3.model.*;
import org.example.project3.query.RequestQuery;
import org.example.project3.utilities.enums.Role;
import org.example.project3.utilities.others.Printer;

import java.time.LocalDateTime;
import java.time.LocalDate;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.jar.Attributes;

public class RequestDAOSQL implements RequestDAO {
    private static final String ID = "id";
    private static final String SCHEDULE = "schedule";
    private static final String EXERCISE = "exercise";
    private static final String REASON = "reason";
    private static final String CUSTOMER = "customer";
    private static final String DATETIME = "datetime";
    private static final String COURSE = "c.name";

    private static final String NAME = "cu.name";
    private static final String SURNAME = "surname";
    private static final String GENDER = "gender";
    private static final String EMAIL = "mail";
    private static final String INJURY = "injury";
    private static final String HOUR = "hour";
    private static final String DATE = "date";
    private static final String BIRTHDAY = "birthday";


    @Override
    public void sendRequest(Request request) {
        try(Connection conn = ConnectionSQL.getConnection()) {
            RequestQuery.sendRequest(conn, request.getSchedule(), request.getExercise(), request.getReason());
        } catch (SQLException | DbOperationException e){
            handleException(e);
        }
    }

    @Override
    public boolean hasAlreadySentRequest(Request request) {
        try (Connection conn = ConnectionSQL.getConnection()) {
            ResultSet rs = RequestQuery.hasAlreadySentARequest(conn, request.getSchedule());
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
    public void removeCourseRequest(Reservation reservation){
        try(Connection conn = ConnectionSQL.getConnection()) {
            RequestQuery.removeCourseRequest(conn, reservation.getCustomer().getCredentials().getMail(), reservation.getCourse().getCourseID(), reservation.getDay(), reservation.getHour());
        }catch (Exception e){
            handleException(e);
        }
    }


    @Override
    public void retrieveCourseRequest(Trainer trainer, List<Reservation> reservationList){

        try(Connection conn = ConnectionSQL.getConnection()){
            ResultSet rs = RequestQuery.retireveCourseRequest(conn, trainer.getCredentials().getMail());
            while(rs.next()){
                Customer customer = new Customer(
                        new Credentials(rs.getString(EMAIL), Role.CLIENT),
                        rs.getString(NAME),
                        rs.getString(SURNAME),
                        rs.getString(GENDER),
                        false,
                        rs.getDate(BIRTHDAY).toLocalDate()
                         // poi da sistemare
                );

                customer.setInjury(rs.getString(INJURY));

                Course course = new Course(rs.getString(COURSE));
                course.setCourseID(rs.getInt(ID));

                Reservation reservation = new Reservation (customer, course, rs.getString(DATE), rs.getString(HOUR));
                reservationList.add(reservation);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCourseRequest(Reservation reservation){

        try(Connection conn = ConnectionSQL.getConnection()) {
            RequestQuery.addCourseRequest(conn, reservation.getCustomer().getCredentials().getMail(), reservation.getCourse().getCourseID(), reservation.getDay(), reservation.getHour());

        }catch (SQLException | DbOperationException e){
            handleException(e);
        }
    }


    @Override
    public boolean alreadyHasReservation(Reservation reservation) {
        try (Connection conn = ConnectionSQL.getConnection()) {
            ResultSet rs = RequestQuery.alreadyHasRequest(conn, reservation);
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException | DbOperationException e) {
            handleException(e);
            return false;
        }
    }

    private void handleException(Exception e) {
        Printer.errorPrint(String.format("%s", e.getMessage()));
    }
}
