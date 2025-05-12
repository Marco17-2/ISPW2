package org.example.project3.dao;

import org.example.project3.model.*;

public interface UpdateDAO {
    boolean mailExists(String mail);

    //metodo per modificare le credenziali
    void modifyCredentials(Credentials newCredentials, Credentials oldCredentials);
    void modifyCustomer(Customer customer);
    void modifyTrainer(Trainer trainer);

    //metodi per la gestione delle richieste
    void deleteRequest(Request request);
}
