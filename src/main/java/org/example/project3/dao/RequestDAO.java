package org.example.project3.dao;

import com.example.bodybuild.model.Request;

public interface RequestDAO {
    //metodi per la gestione delle richieste
    void sendRequest(Request request);
    boolean hasAlreadySentRequest(Request request);
}
