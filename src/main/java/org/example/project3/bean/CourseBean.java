package org.example.project3.bean;

import model.SessionCourse;
import model.Trainer;

import java.util.ArrayList;
import java.util.List;

public class CourseBean {

    private int courseID;
    private String courseName;
    private Trainer trainer;
    private int slots;
    private int duration;
    private String level;
    private List<SessionCourse> sessions;

    public CourseBean(int courseID, String courseName, Trainer trainer, int slots, int duration, String level) {

        this.courseID = courseID;
        this.courseName = courseName;
        this.trainer = trainer;
        this.slots = slots;
        this.duration = duration;
        this.level = level;
        this.sessions = new ArrayList<>();

    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<SessionCourse> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionCourse> sessions) {
        this.sessions = sessions;
    }

    public void addSessions(SessionCourse session){
        this.sessions.add(session);
    }



    //provaprovaprovaprova
}