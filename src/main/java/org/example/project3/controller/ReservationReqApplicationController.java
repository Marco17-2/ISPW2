package org.example.project3.controller;

import org.example.project3.dao.RequestDAO;
import org.example.project3.dao.ReservationDAO;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.patterns.factory.FactoryDAO;
import org.example.project3.patterns.factory.BeanAndModelMapperFactory;

import org.example.project3.beans.*;
import org.example.project3.model.*;

import java.util.ArrayList;
import java.util.List;

public class ReservationReqApplicationController {

    private final BeanAndModelMapperFactory factory;
    private final RequestDAO requestDAO;
    private final ReservationDAO reservationDAO;

    public ReservationReqApplicationController() {
        this.factory = BeanAndModelMapperFactory.getInstance();
        this.requestDAO = FactoryDAO.getRequestDAO();
        this.reservationDAO = FactoryDAO.getReservationDAO();
    }

    public void addReservation(ReservationBean reservationBean){
            Reservation reervation = factory.fromBeanToModel(reservationBean, ReservationBean.class);
            reservationDAO.addReservation(reervation);
    }

    public void deleteReservationReq(ReservationBean reservationBean){
        ReservationManagerConcreteSucjet reservationManagerConcreteSubject = ReservationManagerConcreteSubject.getIstanve();
        Reservation reservation = factory.fromBeanToModel(reservationBean, ReservationBean.class);
        requestDAO.removeCourseRequest(reservation);
        reservationManagerConcreteSubject.removeReservation(reservation);
    }

}
