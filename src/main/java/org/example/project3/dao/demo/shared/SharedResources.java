package org.example.project3.dao.demo.shared;


import org.example.project3.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    // Mappa per tenere traccia delle relazioni pazienti-psicologi
//    private final  Map<String, Psychologist> patientsWithPsychologists = new HashMap<>();
//    private final Map<String, Map<LocalDate,String>> diaryTable= new ConcurrentHashMap<>();//<mail,<data,contenuto>>
//    private final Map<String, List<ToDoItem>> toDoTable= new ConcurrentHashMap<>();//<mail,listaToDo>
//    private final Map<String, List<Task>> taskTable= new ConcurrentHashMap<>();//<mail,listaTask>


    // Singleton per garantire che ci sia una sola istanza della classe
    private static SharedResources instance=null;

    private SharedResources() {
        // Costruttore privato per evitare la creazione di istanze esterne
    }

    // Metodo per ottenere l'istanza Singleton
    public static synchronized SharedResources getInstance() {
        if (instance == null) {
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
//    public Map<String,Psychologist> getPatientsWithPsychologists(){
//        return patientsWithPsychologists;
//    }
    public Map<Long,List<Request>> getRequestsSent(){
        return requestsSent;
    }
//    public Map<String, Map<LocalDate,String>> getDiaryTable(){
//        return diaryTable;
//    }
//    public Map<String, List<ToDoItem>> getToDoTable(){
//        return toDoTable;
//    }
//    public Map<String, List<Task>> getTaskTable(){
//        return taskTable;
//    }

    public Map<String, Course> getCourses() { return courses; }

}

