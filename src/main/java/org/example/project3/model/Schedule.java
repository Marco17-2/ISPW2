package org.example.project3.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    public long id;
    public String name;
    public Customer customer;
    public Trainer trainer;
    public ArrayList<Exercise> exercises;

    public Schedule(long id, String name, Customer customer, Trainer trainer, List<Exercise> exercises) {
        this.id = id;
        this.name = name;
        this.customer = customer;
        this.trainer = trainer;
        this.exercises = (ArrayList<Exercise>) exercises;
    }

    public Schedule(long id, String name, Customer customer, Trainer trainer) {
        this.id = id;
        this.name = name;
        this.customer = customer;
        this.trainer = trainer;
        this.exercises = null;
    }

    public Schedule( String name, Customer customer, Trainer trainer) {
        this.id = 0;
        this.name = name;
        this.customer = customer;
        this.trainer = trainer;
        this.exercises = null;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = (ArrayList<Exercise>) exercises;
    }

    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }
}
