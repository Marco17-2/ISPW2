package org.example.project3.model;


import java.time.Duration;

// correzione costruttore by bean
public class Course {

    private int courseID;
    private String courseName;
    private Trainer trainer;
    private int slots;
    private int remainingSlots;
    private Duration duration;
    private String level;
    private String day;
    private String hour;

    public Course(int courseID, String courseName, Trainer trainer, int slots, int remainingSlots, Duration duration, String level, String day, String hour) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.trainer = trainer;
        this.slots = slots;
        this.duration = duration;
        this.remainingSlots = remainingSlots;
        this.level = level;
        this.day = day;
        this.hour = hour;


    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public int getSlots() {
        return slots;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getLevel() {
        return level;
    }

    public String getDay(){
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getHour() {
        return hour;
    }

    public int getRemainingSlots() {
        return remainingSlots;
    }

    public void setRemainingSlots(int remainingSlots) {
        this.remainingSlots = remainingSlots;
    }
}
