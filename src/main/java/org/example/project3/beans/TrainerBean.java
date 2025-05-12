package org.example.project3.beans;

import java.util.ArrayList;
import java.util.List;

public class TrainerBean extends LoggedUserBean {

    private Integer trainerID;
    private ArrayList<String> specializations;

    public TrainerBean(CredentialsBean credentialsBean, String name, String surname, String gender, boolean isOnline) {
        super(credentialsBean, name, surname, gender, isOnline);
        this.specializations = new ArrayList<>();
    }

    public TrainerBean(CredentialsBean credentialsBean) {
        super(credentialsBean);
        this.specializations = new ArrayList<>();
    }

    public Integer getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(Integer trainerID) {
        this.trainerID = trainerID;
    }

    public ArrayList<String> getSpecializations() {
        return specializations;
    }

    public void addSpecializations(String specialization) {
        specializations.add(specialization);
    }

    public void setSpecializations(List<String> specializations) {
        this.specializations = (ArrayList<String>) specializations;
    }
}
