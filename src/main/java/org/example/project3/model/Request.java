package org.example.project3.model;

import java.time.LocalDateTime;

public class Request {
    private long ID;
    private Schedule schedule;
    private Exercise exercise;
    private String reason;
    private Customer customer;
    private LocalDateTime dateTime;
    private Course course;

    public Request(long ID, Schedule schedule, Exercise exercise, String reason, Customer customer, LocalDateTime dateTime, Course course) {
        this.ID = ID;
        this.schedule = schedule;
        this.exercise = exercise;
        this.reason = reason;
        this.customer = customer;
        this.dateTime = dateTime;
        this.course = course;
    }

    public Request(long ID) {
        this.ID = ID;
        this.schedule = null;
        this.exercise = null;
        this.reason = "";
        this.customer = null;
        this.dateTime = null;
        this.course = null;
    }

    public long getID() {
        return ID;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public String getReason() {
        return reason;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
