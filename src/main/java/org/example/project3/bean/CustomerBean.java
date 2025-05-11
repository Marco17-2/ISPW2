package org.example.project3.bean;

import java.util.ArrayList;
import java.util.List;

public class CustomerBean {

    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private Boolean subscription;
    private int userID;
    private List<String> injuries;

    public CustomerBean(String firstName, String lastName, String gender, String email, Boolean subscription){
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.subscription = subscription;
        this.injuries = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSubscription() {
        return subscription;
    }

    public void setSubscription(Boolean subscription) {
        this.subscription = subscription;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setInjuries(List<String> injuries) {
        this.injuries = injuries;
    }

    public List<String> getInjuries(){
        return injuries;
    }

    public void addInjury(String injury){
        this.injuries.add(injury);
    }
}
