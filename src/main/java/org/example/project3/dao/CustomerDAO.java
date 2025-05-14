package org.example.project3.dao;

import org.example.project3.exceptions.MailAlreadyExistsException;
import org.example.project3.model.Credentials;
import org.example.project3.model.Customer;

public interface CustomerDAO {
    boolean emailExists(String email);
    boolean insertUser(Credentials credentials);
    void registerCustomer(Customer customer) throws MailAlreadyExistsException;
    void retrieveCustomer(Customer customer);
    void removeCustomer(Customer customer);
    void modifyCustomer(Customer customer);
}
