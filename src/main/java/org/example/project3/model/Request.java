package org.example.project3.model;

import java.time.LocalDateTime;

public class Request {
    private long ID;
    private Schedule schedule;
    private Exercise exercise;
    private String reason;
    private LocalDateTime dateTime;

    public Request(long ID, Schedule schedule, Exercise exercise, String reason, LocalDateTime dateTime) {
        this.ID = ID;
        this.schedule = schedule;
        this.exercise = exercise;
        this.reason = reason;
        this.dateTime = dateTime;
    }

    public Request(Schedule schedule) {
        this.ID = 0;
        this.schedule = schedule;
        this.exercise = null;
        this.reason = "";
        this.dateTime = null;
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

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
