package org.example.project3.dao.demo.shared;


import org.example.project3.model.*;
import org.example.project3.utilities.enums.RestTime;
import org.example.project3.utilities.enums.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SharedResources {

    // Mappe condivise per i vari entità
    private final Map<String, Trainer> trainers = new ConcurrentHashMap<>();
    private final Map<String, Course> courses = new ConcurrentHashMap<>();
    private final Map<String, Customer> customers = new ConcurrentHashMap<>();
    private final Map<Integer, Subscription> subscriptions = new ConcurrentHashMap<>();
    private final Map<Long, Schedule> schedules = new ConcurrentHashMap<>();
    private final Map<Long, Exercise> exercises = new ConcurrentHashMap<>();
    private final Map<String, Credentials> userTable = new ConcurrentHashMap<>();
    private final Map<Long, List<Request>> requestsSent = new HashMap<>();

    //associazione corso-trainer
    private final Map<String, Trainer> trainerCourse = new ConcurrentHashMap<>();

    //Verificarre l'utilità:

    private final Map<String, List<Reservation>> reservations = new HashMap<>();
    private final Map<String, List<Reservation>> reservationRequests = new HashMap<>();

    // Mappa per tenere traccia delle relazioni pazienti-psicologi
    private final  Map<String, List<Schedule>> customerSchedules = new HashMap<>();
    private final Map<Long,List<Exercise>> exerciseSchedules = new HashMap<>();


    // Singleton per garantire che ci sia una sola istanza della classe
    private static SharedResources instance=null;

    private SharedResources() {
    }

    // Metodo per ottenere l'istanza Singleton
    public static synchronized SharedResources getInstance() {
        if(instance==null){
            instance = new SharedResources();
        }
        return instance;
    }

    // Getter per le mappe condivise
    public Map<String, Trainer> getTrainers() {
        return trainers;
    }

    public Map<String, Customer> getCustomers() {
        return customers;
    }

    public Map<Integer, Subscription> getSubscriptions() {
        return subscriptions;
    }
    public Map<Long,Schedule> getSchedules(){
        return schedules;
    }
    public Map<Long,Exercise> getExercises(){
        return exercises;
    }
    public Map<String,Credentials> getUserTable(){
        return userTable;
    }
    public Map<String,List<Schedule>> getCustomerSchedules(){
        return customerSchedules;
    }
    public Map<Long,List<Request>> getRequestsSent(){
        return requestsSent;
    }

    public Map<String, Course> getCourses() { return courses; }

    public Map<String, List<Reservation>> getReservations() { return reservations; }
    public Map<String, List<Reservation>> getReservationRequests() { return reservationRequests; }
    public Map<String, Trainer> getTrainerCourse() {
        return trainerCourse;
    }
    public Map<Long, List<Exercise>> getExerciseSchedules() {
        return exerciseSchedules;
    }



}

