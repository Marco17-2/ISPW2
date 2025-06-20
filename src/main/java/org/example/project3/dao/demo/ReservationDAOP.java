package org.example.project3.dao.demo;

import org.example.project3.dao.ReservationDAO;
import org.example.project3.dao.demo.shared.SharedResources;
import org.example.project3.model.Customer;
import org.example.project3.model.Reservation;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class ReservationDAOP implements ReservationDAO {

    @Override
    public void removeReservation(Reservation reservation) {

        List<Reservation> reservations = SharedResources.getInstance().getReservations().get(reservation.getCustomer().getCredentials().getMail());

        reservations.removeIf(r ->
                r.getCourse().equals(reservation.getCourse()) &&
                        r.getCustomer().equals(reservation.getCustomer()) &&
                        r.getDay().equals(reservation.getDay()) &&
                        r.getHour().equals(reservation.getHour())
        );

        SharedResources.getInstance().getReservations().remove(reservation.getCustomer().getCredentials().getMail());

        SharedResources.getInstance().getReservations().put(reservation.getCustomer().getCredentials().getMail(), reservations);
    }

    @Override
    public void addReservation(Reservation reservation) {

        String customer = reservation.getCustomer().getCredentials().getMail();

        // Aggiunge la prenotazione alla mappa
        SharedResources.getInstance().getReservations().computeIfAbsent(customer, k -> new ArrayList<>()).add(reservation);

    }

    @Override
    public void retrieveReservation(Customer customer, List<Reservation> reservations) {

        reservations.addAll(SharedResources.getInstance().getReservations().get(customer.getCredentials().getMail()));

    }
}
