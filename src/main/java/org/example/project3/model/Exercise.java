package org.example.project3.model;

import java.time.Duration;

public class Exercise {
    public long id;
    public String name;
    public String description;
    public Integer numberSeries;
    public Integer numberReps;
    public Duration restTime;

    public Exercise(long id, String name, String description, Integer numberSeries, Integer numberReps, Duration restTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberSeries = numberSeries;
        this.numberReps = numberReps;
        this.restTime = restTime;
    }

    public Exercise(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberSeries = 0;
        this.numberReps = 0;
        this.restTime = null;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumberSeries(Integer numberSeries) {
        this.numberSeries = numberSeries;
    }

    public void setNumberReps(Integer numberReps) {
        this.numberReps = numberReps;
    }

    public void setRestTime(Duration restTime) {
        this.restTime = restTime;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getNumberSeries() {
        return numberSeries;
    }

    public Integer getNumberReps() {
        return numberReps;
    }

    public Duration getRestTime() {
        return restTime;
    }

}
