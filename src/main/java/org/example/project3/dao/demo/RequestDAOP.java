package org.example.project3.dao.demo;

import org.example.project3.dao.RequestDAO;
import org.example.project3.dao.demo.shared.SharedResources;
import org.example.project3.model.Course;
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
        // Verifica se esiste già una richiesta per la coppia paziente-psicologo
        if (SharedResources.getInstance().getRequestsSent().containsKey(request.getSchedule().getCustomer())) {
            for (Request existingRequest : SharedResources.getInstance().getRequestsSent().get(request.getSchedule().getTrainer())) {
                if (existingRequest.getSchedule().getCustomer().getCredentials().getMail().equals(request.getSchedule().getCustomer().getCredentials().getMail())) {
                    return true;  // Se la richiesta esiste già, restituisce true
                }
            }
        }
        return false;  // Altrimenti, non è stata inviata una richiesta
    }

    @Override
    public void deleteRequest(Request request) {
        SharedResources.getInstance().getRequestsSent().remove(request.getID());
    }



    // chiave per la lista Corso,
    @Override
    public void retrieveCourseRequest(Course course, List<Reservation> reservationList){
        reservationList.addAll(SharedResources.getInstance().getReservationRequests().get(course.getCourseName()));
    }


    @Override
    public void removeCourseRequest(Reservation reservation){

        List<Reservation> reservationsRequests = SharedResources.getInstance().getReservationRequests().get(reservation.getCustomer().getCredentials().getMail());

        reservationsRequests.removeIf(r->
                        r.getCourse().equals(reservation.getCourse()) &&
                                r.getCustomer().equals(reservation.getCustomer()) &&
                                r.getDate().equals(reservation.getDate()) &&
                                r.getHour().equals(reservation.getHour())
                );

        //il corso identifica la lista precisa
        SharedResources.getInstance().getReservationRequests().remove(reservation.getCourse().getCourseName());

        SharedResources.getInstance().getReservationRequests().put(reservation.getCourse().getCourseName(), reservationsRequests);
    }


    @Override
    public void addCourseRequest(Reservation reservation){

        String course = reservation.getCourse().getCourseName();

        // Aggiunge la prenotazione alla mappa
        SharedResources.getInstance().getReservationRequests() .computeIfAbsent(course, k -> new ArrayList<>()).add(reservation);
    }
    }


