package org.example.project3.model;

import java.util.List;

public class Customer {

    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String password;
    private List<String> injuries;
    private Boolean subscription;
    private int userID;

    public void setCredentialsByBean(clientBean clientbean){
        this.firstName = clientbean.getFirstName();
        this.lastName = clientbean.getLastName();
        this.gender = clientbean.getGender();
        this.email = clientbean.getEmail();
        this.password = clientbean.getPassword();

    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSubscription(Boolean subscription) {
        this.subscription = subscription;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void getFirstName(){
        return this.firstName;
    }

    public void getLastName(){
        return this.lastName;
    }

    public void getGender(){
        return this.gender;
    }

    public void getEmail(){
        return this.email;
    }

    public void getPassword(){
        return this.password;
    }

    public void getSubscription(){
        return this.subscription;
    }

    public void getUserID(){
        return this.userID;
    }

    public List<String> getInjuries() {
        return injuries;
    }

    public void setInjuries(List<String> injuries) {
        this.injuries = injuries;
    }

    public void addInjuries(String injuries){
        this.injuries.add(injuries);
    }
}
