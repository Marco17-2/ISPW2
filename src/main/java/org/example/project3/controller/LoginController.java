package org.example.project3.controller;


import org.example.project3.dao.LoginAndRegistrationDAO;
import org.example.project3.patterns.factory.BeanAndModelMapperFactory;
import org.example.project3.patterns.factory.FactoryDAO;

/*********Parte del caso d'uso: Login*********/
public class LoginController {
    private final BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final LoginAndRegistrationDAO loginGeneric;
    // Costruttore
    public LoginController(){
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
        this.loginGeneric = FactoryDAO.getDAO();
    }
}
