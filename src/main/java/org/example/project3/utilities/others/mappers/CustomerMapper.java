package org.example.project3.utilities.others.mappers;

import org.example.project3.beans.CustomerBean;
import org.example.project3.model.Customer;

public class CustomerMapper implements BeanAndModelMapper<CustomerBean,Customer> {
    private final CredentialsMapper credentialsMapper = new CredentialsMapper();
    @Override
    public Customer fromBeanToModel(CustomerBean bean) {
        if(bean.getName() != null)
            return new Customer(credentialsMapper.fromBeanToModel(bean.getCredentialsBean()), bean.getName(), bean.getSurname(), bean.getGender(), bean.isOnline());
        else
            return new Customer(credentialsMapper.fromBeanToModel(bean.getCredentialsBean()));
    }

    @Override
    public CustomerBean fromModelToBean(Customer model) {
        if(model.getName() != null)
            return new CustomerBean(credentialsMapper.fromModelToBean(model.getCredentials()), model.getName(), model.getSurname(), model.getGender(), model.isOnline());
        else
            return new CustomerBean(credentialsMapper.fromModelToBean(model.getCredentials()));
    }

}
