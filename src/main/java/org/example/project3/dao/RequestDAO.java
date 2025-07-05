package org.example.project3.dao;


import org.example.project3.model.Request;
import org.example.project3.model.Trainer;

import org.example.project3.model.Reservation;

import java.util.List;

public interface RequestDAO {
    //metodi per la gestione delle richieste
    void sendRequest(Request request);
    boolean hasAlreadySentRequest(Request request);
    void deleteRequest(Request request);

    void retrieveCourseRequest(Trainer trainer, List<Reservation> reservationList);
    void removeCourseRequest(Reservation reservation);
    void addCourseRequest(Reservation reservation);
    boolean alreadyHasReservation(Reservation reservation);
}
