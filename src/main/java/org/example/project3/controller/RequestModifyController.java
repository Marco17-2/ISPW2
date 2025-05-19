package org.example.project3.controller;

import org.example.project3.beans.CustomerBean;
import org.example.project3.beans.RequestBean;
import org.example.project3.beans.ScheduleBean;
import org.example.project3.beans.TrainerBean;
import org.example.project3.dao.RequestDAO;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.model.Customer;
import org.example.project3.model.Request;
import org.example.project3.model.Schedule;
import org.example.project3.model.Trainer;
import org.example.project3.patterns.factory.BeanAndModelMapperFactory;
import org.example.project3.patterns.factory.FactoryDAO;
import org.example.project3.patterns.observer.RequestManagerConcreteSubject;

public class RequestModifyController {
    private final BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final RequestDAO requestDAO;

    //COstruttore
    public RequestModifyController(){
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
        this.requestDAO = FactoryDAO.getRequestDAO();
    }

    //metodo per eliminare una richiesta
    public void deleteRequest(RequestBean requestBean) {
        RequestManagerConcreteSubject requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();  //recupero istanza del ConcreteSubject
        Request request = beanAndModelMapperFactory.fromBeanToModel(requestBean, RequestBean.class);
        requestDAO.deleteRequest(request);
        requestManagerConcreteSubject.removeRequest(request);   //rimuovo la richiesta dalla lista salvata nel ConcreteSubject
    }

    //metodo per creare una richiesta
    public RequestBean createRequestBean(Request request) {
        return beanAndModelMapperFactory.fromModelToBean(request, Request.class);
    }

    //Metodo per inviare una richiesta
    public void sendRequest(RequestBean requestBean) throws NoResultException {
        Request request = beanAndModelMapperFactory.fromBeanToModel(requestBean, RequestBean.class);
        try{
            requestDAO.sendRequest(request);
            RequestManagerConcreteSubject requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();
            requestManagerConcreteSubject.addRequest(request);
        } catch (Exception e){
            throw new NoResultException("Errore nell'invio della richiesta",e);

        }
    }

    //Metodo per controllare se è già stata inviata una richiesta
    public boolean hasAlreadySentARequest(ScheduleBean scheduleBean) throws NoResultException {
        Schedule schedule = beanAndModelMapperFactory.fromBeanToModel(scheduleBean, ScheduleBean.class);
        Request request= new Request(schedule);
        try{
            return requestDAO.hasAlreadySentRequest(request);
        } catch (Exception e){
            throw new NoResultException("Errore nel controllo delle richieste",e);
        }
    }
}
