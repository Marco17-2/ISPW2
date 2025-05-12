package org.example.project3.model;

public abstract class LoggedUser {
    private Credentials credentials;
    private String name;
    private String surname;
    private String gender;
    private boolean online;

    protected LoggedUser(Credentials credentials, String name, String surname, String gender, boolean isOnline) {
        this.credentials = credentials;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.online = isOnline;
    }

    protected  LoggedUser(Credentials credentials){
        this.credentials = credentials;
        this.name = null;
        this.surname = null;
        this.gender = null;
        this.online = false;
    }
    //getter e setter per i nuovi campi

    public Credentials getCredentials() {
        return credentials;
    }

    //FORSE DA TOGLIERE
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public Boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

}