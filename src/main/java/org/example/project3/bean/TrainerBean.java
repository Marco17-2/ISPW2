package org.example.project3.bean;

import java.util.Vector;

public class TrainerBean {

    private int trainerID;
    private String firstName;
    private String lastName;
    private String email;
    private Vector<String> specializations;

    public TrainerBean(int traienrID, String firstName, String lastName, String email, Vector<String> specializations){
        this.trainerID = traienrID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.specializations = specializations;

    }

    public int getTrainerID() {
        return trainerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Vector<String> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Vector<String> specializations) {
        this.specializations = specializations;
    }

    public void setTrainerID(int trainerID) {
        this.trainerID = trainerID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addSpecialization(String specialization){
        this.specializations.add(specialization);
    }
}
