package org.example.project3.controller;


import com.example.bodybuild.dao.LoginAndRegistrationDAO;
import com.example.bodybuild.patterns.factory.BeanAndModelMapperFactory;
import com.example.bodybuild.patterns.factory.FactoryDAO;

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
