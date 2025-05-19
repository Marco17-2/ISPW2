package org.example.project3.dao.demo;

import org.example.project3.dao.RequestDAO;
import org.example.project3.dao.demo.shared.SharedResources;
import org.example.project3.model.Request;

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
                if (existingRequest.getCustomer().getCredentials().getMail().equals(request.getCustomer().getCredentials().getMail())) {
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
}
