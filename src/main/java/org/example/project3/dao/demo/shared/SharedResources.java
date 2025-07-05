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

//    private SharedResources() {
//        // Costruttore privato per evitare la creazione di istanze esterne
//    }
    private SharedResources() {
        // Costruttore privato per evitare la creazione di istanze esterne
        // Inizializza con dati di esempio qui
        populateSampleData(); //
    }

    // Metodo per ottenere l'istanza Singleton
    public static synchronized SharedResources getInstance() {
//        if (instance == null) {
            instance = new SharedResources();
//        }
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

    // Helper method to populate all sample data
    private void populateSampleData() {
        // 1. Create Credentials
        Credentials customer1Creds = new Credentials("mario.rossi@gmail.com", "mariorossi", Role.CLIENT);
        Credentials trainer1Creds = new Credentials("mattia.verdi@gmail.com", "mattiaverdi", Role.TRAINER);

        userTable.put(customer1Creds.getMail(), customer1Creds);
        userTable.put(trainer1Creds.getMail(), trainer1Creds);

        // 2. Create Customer and Trainer objects
        Customer customer1 = new Customer(customer1Creds, "Mario", "Rossi","Maschio",false, LocalDate.of(2000,04,12));
        customers.put(customer1.getCredentials().getMail(), customer1);

        Trainer trainer1 = new Trainer(trainer1Creds, "Mattia", "Verdi","Maschio",false, LocalDate.of(2000,04,12));
        trainers.put(trainer1.getCredentials().getMail(), trainer1);

        // 3. Create Exercises
        Exercise exercise1 = new Exercise(1,"Push-ups","Standard push-up exercise.",3,10, RestTime.SECONDS60);
        exercises.put(exercise1.getId(),  exercise1);
        Exercise exercise2 = new Exercise(2,"Squats","Bodyweight squats.",3,10, RestTime.SECONDS60);
        exercises.put(exercise2.getId(),  exercise2);
        Exercise exercise3 = new Exercise(3,"Plank","Core plank hold.",3,10, RestTime.SECONDS60);
        exercises.put(exercise3.getId(), exercise3);

        // 4. Create Schedules
        // Schedule 1 for customer1, by trainer1
        List<Exercise> exercisesForScheduleA = new ArrayList<>();
        exercisesForScheduleA.add(exercise1);
        exercisesForScheduleA.add(exercise2);
        Schedule scheduleA = new Schedule(1,"Beginner Full Body",customer1,trainer1,exercisesForScheduleA);
        schedules.put(scheduleA.getId(), scheduleA);// Add to general schedules map
        exerciseSchedules.put(scheduleA.getId(),exercisesForScheduleA);


        // Schedule 2 for customer1, by trainer1
        List<Exercise> exercisesForScheduleB = new ArrayList<>();
        exercisesForScheduleB.add(exercise3);
        Schedule scheduleB = new Schedule(2,"Cardio Focus",customer1,trainer1,exercisesForScheduleB);
        schedules.put(scheduleB.getId(), scheduleB);
        exerciseSchedules.put(scheduleB.getId(),exercisesForScheduleB);// Add to general schedules map

        // Schedule 3 for customer2, by trainer1
        List<Exercise> exercisesForScheduleC = new ArrayList<>();
        exercisesForScheduleC.add(exercise1);
        exercisesForScheduleC.add(exercise2);
        exercisesForScheduleC.add(exercise3);
        Schedule scheduleC = new Schedule(3,"Advanced Strength",customer1,trainer1,exercisesForScheduleC);
        schedules.put(scheduleC.getId(), scheduleC); // Add to general schedules map
        exerciseSchedules.put(scheduleC.getId(),exercisesForScheduleC);

        // 5. Populate customerSchedules map (crucial for retrieveSchedule)
        List<Schedule> customer1Schedules = new ArrayList<>();
        customer1Schedules.add(scheduleA);
        customer1Schedules.add(scheduleB);
        customer1Schedules.add(scheduleC);
        customerSchedules.put(customer1.getCredentials().getMail(), customer1Schedules);


        // Optional: Add some Courses and Subscriptions if you plan to test those
        Course course1 = new Course(1,"Yoga Basics",30,30,"60 min","basso", "martedì", "14");
        courses.put(course1.getCourseName(), course1);
        trainerCourse.put(course1.getCourseName(), trainer1);

        Reservation reservation1=new Reservation(customer1,course1,"martedì", "14");
        Reservation reservation2=new Reservation(customer1,course1,"giovedì", "14");
        List<Reservation> reservationList=new ArrayList<>();
        reservationList.add(reservation1);
        reservationList.add(reservation2);
        reservationRequests.put(trainer1.getCredentials().getMail(),reservationList);

    }


}

