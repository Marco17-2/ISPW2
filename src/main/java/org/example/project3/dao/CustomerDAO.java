package org.example.project3.dao;

import org.example.project3.model.Customer;

public interface CustomerDAO {
    void registerCustomer(Customer customer);
    void retrieveCustomer(Customer customer);
    void removeCustomer(Customer customer);
    void modifyCustomer(Customer customer);
}
