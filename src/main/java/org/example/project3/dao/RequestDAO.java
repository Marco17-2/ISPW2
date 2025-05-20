package org.example.project3.dao;

import org.example.project3.model.Customer;
import org.example.project3.model.Request;
import org.example.project3.model.Trainer;
import org.example.project3.model.Course;
import org.example.project3.model.ReservationReq;

import java.time.LocalDate;

import java.util.List;

public interface RequestDAO {
    //metodi per la gestione delle richieste
    void sendRequest(Request request);
    boolean hasAlreadySentRequest(Request request);
//    void retrieveCustomerRequest(Customer customer, List<Request> requests);
    void deleteRequest(Request request);

    void retrieveCourseRequest(Course course, List<ReservationReq> reservationList);
    void removeCourseRequest(ReservationReq reservationReq);
    void addCourseRequest(Course course, Customer customer, LocalDate date, String hour);
}
