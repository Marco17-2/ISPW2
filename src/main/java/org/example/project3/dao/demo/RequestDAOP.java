package org.example.project3.dao.demo;

import org.example.project3.dao.RequestDAO;
import org.example.project3.dao.demo.shared.SharedResources;

import org.example.project3.model.Trainer;
import org.example.project3.model.Request;
import org.example.project3.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class RequestDAOP implements RequestDAO {
    @Override
    public void sendRequest(Request request) {
        // Usa computeIfAbsent per evitare il controllo esplicito e creazione della lista
        SharedResources.getInstance().getRequestsSent()
                .computeIfAbsent(request.getID(), k -> new ArrayList<>()).add(request);
    }

    @Override
    public boolean hasAlreadySentRequest(Request request) {
        Long scheduleId = request.getID();

        // Controlla se esiste una lista di richieste per questo scheduleId
        if (SharedResources.getInstance().getRequestsSent().containsKey(scheduleId)) {
            // Itera sulla lista di richieste associate a questo scheduleId
            for (Request existingRequest : SharedResources.getInstance().getRequestsSent().get(scheduleId)) {
                // Confronta le email del cliente e del trainer per verificare se la richiesta è la stessa
                if (existingRequest.getSchedule().getCustomer().getCredentials().getMail().equals(request.getSchedule().getCustomer().getCredentials().getMail()) &&
                        existingRequest.getSchedule().getTrainer().getCredentials().getMail().equals(request.getSchedule().getTrainer().getCredentials().getMail())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void deleteRequest(Request request) {
        SharedResources.getInstance().getRequestsSent().remove(request.getID());
    }



    // chiave per la lista Corso,
    @Override
    public void retrieveCourseRequest(Trainer trainer, List<Reservation> reservationList){
        reservationList.addAll(SharedResources.getInstance().getReservationRequests().get(trainer.getCredentials().getMail()));
    }


    @Override
    public void removeCourseRequest(Reservation reservation){
        Trainer trainer = SharedResources.getInstance().getTrainerCourse().get(reservation.getCourse().getCourseName());
        List<Reservation> reservationsRequests = SharedResources.getInstance().getReservationRequests().get(trainer.getCredentials().getMail());

        reservationsRequests.removeIf(r->
                        r.getCourse().equals(reservation.getCourse()) &&
                                r.getCustomer().equals(reservation.getCustomer()) &&
                                r.getDay().equals(reservation.getDay()) &&
                                r.getHour().equals(reservation.getHour())
                );

        //il corso identifica la lista precisa
        SharedResources.getInstance().getReservationRequests().remove(reservation.getCourse().getCourseName());

        SharedResources.getInstance().getReservationRequests().put(reservation.getCourse().getCourseName(), reservationsRequests);
    }


    @Override
    public void addCourseRequest(Reservation reservation){
        Trainer trainer = SharedResources.getInstance().getTrainerCourse().get(reservation.getCourse().getCourseName());

        // Aggiunge la prenotazione alla mappa
        SharedResources.getInstance().getReservationRequests() .computeIfAbsent(trainer.getCredentials().getMail(), k -> new ArrayList<>()).add(reservation);
    }

    @Override
    public boolean alreadyHasReservation(Reservation reservation) {
        Trainer trainer = SharedResources.getInstance().getTrainerCourse().get(reservation.getCourse().getCourseName());

        if (SharedResources.getInstance().getReservationRequests().containsKey(trainer.getCredentials().getMail())) {
            for (Reservation existingReservation : SharedResources.getInstance().getReservationRequests().get(trainer.getCredentials().getMail())) {
                if (existingReservation.getCourse().getCourseName().equals(reservation.getCourse().getCourseName())
                        && existingReservation.getCustomer().getCredentials().getMail().equals(reservation.getCustomer().getCredentials().getMail())
                        && existingReservation.getHour().equals(reservation.getHour())
                        && existingReservation.getDay().equals(reservation.getDay())){
                    return true;
                }
            }
        }
        return false;
    }

    }


