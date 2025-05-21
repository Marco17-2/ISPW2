package org.example.project3.dao.full.sql;

import org.example.project3.dao.ReservationDAO;
import org.example.project3.exceptions.DAOException;
import org.example.project3.exceptions.DbOperationException;
import org.example.project3.model.*;
import org.example.project3.query.RequestQuery;
import org.example.project3.query.ReservationQuery;

import java.time.LocalDate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReservationDAOSQL implements ReservationDAO {

    private static final String COURSE = "course";
    private static final String DATE = "date";
    private static final String HOUR = "hour";

    @Override
    public void removeReservation(Reservation reservation) {
        try (Connection conn = ConnectionSQL.getConnection()) {
            ReservationQuery.removeReservation(conn, reservation.getCourse().getCourseName(), reservation.getCustomer().getCredentials().getMail(), reservation.getDate(), reservation.getHour());
        } catch (SQLException | DbOperationException e) {
            throw new DAOException("Errore rimozione reservation", e);
        }
    }

    @Override
    public void addReservation(Reservation reservation){

            try(Connection conn = ConnectionSQL.getConnection()) {
                ReservationQuery.addRequest(conn, reservation.getCourse().getCourseName(),reservation.getCustomer().getCredentials().getMail(), reservation.getDate(), reservation.getHour());
            }catch (SQLException | DbOperationException e){
                throw new DAOException("Errore rimozione reservation", e);
        }
    }

    @Override
    public void retrieveReservation(Customer customer, List<Reservation> reservations) {

        try (Connection conn = ConnectionSQL.getConnection()) {
            ResultSet rs = ReservationQuery.retrieveReservation(conn, customer.getCredentials().getMail());
            while (rs.next()) {
                Course course = new Course(rs.getString(COURSE));
                Reservation reservation = new Reservation(customer, course, rs.getDate(DATE), rs.getString(HOUR));
                reservations.add(reservation);
            }

        } catch (SQLException e) {
            throw new DAOException("Errore recupero reservation", e);
        }
    }

}
