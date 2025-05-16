package org.example.project3.dao;

import org.example.project3.model.Customer;
import org.example.project3.model.Request;

import java.util.List;

public interface RequestDAO {
    //metodi per la gestione delle richieste
    void sendRequest(Request request);
    boolean hasAlreadySentRequest(Request request);
//    void retrieveCustomerRequest(Customer customer, List<Request> requests);
    void deleteRequest(Request request);
}
