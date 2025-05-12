package org.example.project3.dao;

import com.example.bodybuild.model.*;

public interface UpdateDAO {
    boolean mailExists(String mail);

    //metodo per modificare le credenziali
    void modifyCredentials(Credentials newCedetìntials, Credentials oldCredentials);
    void modifyCustomer(Customer customer);
    void modifyTrainer(Trainer trainer);

    //metodi per la gestione delle richieste
    void deleteRequest(Request request);
}
