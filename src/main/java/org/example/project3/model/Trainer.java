package org.example.project3.model;

import java.util.Vector;

public class Trainer {

    private int trainerID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Vector <String> specializations;

    public void setCredentialsByBean(TrainerBean trainerBean){

        this.firstName = trainerBean.getFirstName();
        this.lastName = trainerBean.getLastName();
        this.email = trainerBean.getEmail();
        this.password = trainerBean.getPassword();
        this.specializations = trainerBean.getSpecializations();

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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSpecializations(Vector<String> specializations) {
        this.specializations = (Vector<String>)specializations;
    }

    public void setTrainerID(int trainerID) {
        this.trainerID = trainerID;
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

    public String getPassword() {
        return password;
    }

    public void addSpecializatinos(String specializations){
        this.specializations.add(specializations);
    }

    public Vector<String> getSpecializations() {
        return specializations;
    }
}
