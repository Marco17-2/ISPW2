package org.example.project3.dao;

import org.example.project3.model.Request;

public interface RequestDAO {
    //metodi per la gestione delle richieste
    void sendRequest(Request request);
    boolean hasAlreadySentRequest(Request request);
    void deleteRequest(Request request);
}
