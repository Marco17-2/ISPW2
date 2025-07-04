package org.example.project3.model;

import org.example.project3.utilities.enums.RestTime;

public class Exercise {
    public long id;
    public String name;
    public String description;
    public Integer numberSeries;
    public Integer numberReps;
    public RestTime restTime;

    public Exercise(long id, String name, String description, Integer numberSeries, Integer numberReps, RestTime restTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberSeries = numberSeries;
        this.numberReps = numberReps;
        this.restTime = restTime;
    }

    public Exercise( String name, String description, Integer numberSeries, Integer numberReps, RestTime restTime) {
        this.id = 0;
        this.name = name;
        this.description = description;
        this.numberSeries = numberSeries;
        this.numberReps = numberReps;
        this.restTime = restTime;
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

    public void setRestTime(RestTime restTime) {
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

    public RestTime getRestTime() {
        return restTime;
    }

    public void setId(long id) {
        this.id = id;
    }
}
