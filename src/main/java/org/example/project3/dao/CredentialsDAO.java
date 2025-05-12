package org.example.project3.dao;

import org.example.project3.model.Credentials;

public interface CredentialsDAO {
    boolean emailExists(String email);
    boolean insertUser(Credentials credentials);
    void login(Credentials credentials);
    void modifyCredentials(Credentials newCredentials, Credentials oldCredentials);
}
