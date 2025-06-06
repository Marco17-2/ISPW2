package org.example.project3.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainerBean extends LoggedUserBean {

    private ArrayList<String> specializations;

    public TrainerBean(CredentialsBean credentialsBean, String name, String surname, String gender, boolean isOnline, LocalDate birthday) {
        super(credentialsBean, name, surname, gender, isOnline, birthday);
        this.specializations = new ArrayList<>();
    }

    public TrainerBean(CredentialsBean credentialsBean) {
        super(credentialsBean);
        this.specializations = new ArrayList<>();
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
