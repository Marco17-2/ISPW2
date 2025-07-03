package org.example.project3.dao.full.sql;

import org.example.project3.dao.ScheduleDAO;
import org.example.project3.exceptions.DAOException;
import org.example.project3.exceptions.DbOperationException;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.model.*;
import org.example.project3.query.ScheduleQuery;
import org.example.project3.utilities.enums.RestTime;
import org.example.project3.utilities.enums.Role;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAOSQL implements ScheduleDAO {

    private static final String ID="id";
    private static final String NAME="name";
    private static final String CUSTOMER="customer";
    private static final String TRAINER="trainer";
    private static final String DESCRIPTION="description";
    private static final String NUMBERSERIES="numberSeries";
    private static final String NUMBERREPS="numberReps";
    private static final String RESTTIME="restTime";

    @Override
    public void addSchedule(Schedule schedule){
        try (Connection conn = ConnectionSQL.getConnection()) {
            ScheduleQuery.addSchedule(conn, schedule);
        } catch (SQLException | DbOperationException e) {
            handleException(e);
        }
    }


    @Override
    public void retrieveSchedule(Customer customer, List<Schedule> schedules) {
        try (Connection conn = ConnectionSQL.getConnection();
             ResultSet rs = ScheduleQuery.retrieveSchedules(conn, customer.getCredentials().getMail())) {

            // --- AGGIUNGI QUI ---
            System.out.println("DEBUG - DAO: Eseguita query retrieveSchedules per customer: " + customer.getCredentials().getMail());
            boolean hasResults = false;
            if (rs.isBeforeFirst()) { // Verifica se il ResultSet contiene righe
                hasResults = true;
                System.out.println("DEBUG - DAO: ResultSet contiene risultati.");
            } else {
                System.out.println("DEBUG - DAO: ResultSet VUOTO. Nessuna scheda trovata nel DB.");
            }
            // --- FINE AGGIUNTA ---

            while (rs.next()) {
                // --- AGGIUNGI QUI ---
                System.out.println("DEBUG - DAO: Trovata scheda - Nome: " + rs.getString(NAME) + ", Trainer: " + rs.getString(TRAINER));
                // --- FINE AGGIUNTA ---
                Schedule schedule = new Schedule(
                        rs.getInt(ID),
                        rs.getString(NAME),
                        new Customer(new Credentials(customer.getCredentials().getMail(), Role.CLIENT)),
                        new Trainer(new Credentials(rs.getString(TRAINER), Role.TRAINER))
                );
                schedules.add(schedule);
            }
            // --- AGGIUNGI QUI ---
            System.out.println("DEBUG - DAO: Dopo ciclo ResultSet, 'schedules' list size: " + schedules.size());
            // --- FINE AGGIUNTA ---
        } catch (SQLException e) {
            handleException(e);
            System.out.println("DEBUG - DAO: Errore SQL in retrieveSchedule: " + e.getMessage()); // Aggiungi log per eccezioni
        }
    }


//    @Override
//    public void retrieveSchedule(Customer customer,List<Schedule> schedules) {
//        try (Connection conn = ConnectionSQL.getConnection();
//             ResultSet rs = ScheduleQuery.retrieveSchedules(conn, customer.getCredentials().getMail())){
//            while (rs.next()) {
//                Schedule schedule = new Schedule(
//                        rs.getString(NAME),
//                        new Customer(new Credentials(rs.getString(CUSTOMER), Role.CLIENT)),
//                        new Trainer(new Credentials(rs.getString(TRAINER), Role.TRAINER)));
//            }
//        } catch (SQLException e) {
//            handleException(e);
//        }
//    }

    public void retrieveExercises(Schedule schedule) {
        // ASSICURATI SEMPRE che la lista degli esercizi sull'oggetto Schedule sia inizializzata.
        // Se schedule.exercises fosse null (come da alcuni costruttori di Schedule),
        // potresti avere un NPE se provi ad aggiungere elementi prima di inizializzarla.
        if (schedule.getExercises() == null) {
            schedule.setExercises(new ArrayList<>());
        } else {
            schedule.getExercises().clear(); // Pulisci la lista se è già esistente per nuovi risultati
        }

        try (Connection conn = ConnectionSQL.getConnection();
             ResultSet rs = ScheduleQuery.retrieveExercises(conn, schedule)){

            // CAMBIATO: da 'if' a 'while' per recuperare TUTTI gli esercizi
            while (rs.next()) {
                Exercise exercise = new Exercise(
                        rs.getInt(ID),
                        rs.getString(NAME),
                        rs.getString(DESCRIPTION),
                        rs.getInt(NUMBERSERIES),
                        rs.getInt(NUMBERREPS),
                        RestTime.convertIntToRestTime(rs.getInt(RESTTIME))
                );
                schedule.addExercise(exercise); // Aggiungi direttamente all'oggetto Schedule
            }
            // Non è più necessario chiamare schedule.setExercises(exercises) qui,
            // poiché gli esercizi vengono aggiunti direttamente.
            // In caso di nessun risultato, la lista sarà semplicemente vuota.

        } catch (SQLException e) {
            handleException(e);
            // Se si verifica un'eccezione SQL, la lista 'exercises' sull'oggetto 'schedule'
            // sarà comunque una ArrayList vuota (grazie al blocco if iniziale), prevenendo il NPE.
            System.err.println("DEBUG - DAO: Errore SQL durante il recupero degli esercizi: " + e.getMessage());
        }
    }

//    @Override
//    public void retrieveExercises(Schedule schedule) {
//        List<Exercise> exercises = new ArrayList<>();
//        try (Connection conn = ConnectionSQL.getConnection();
//             ResultSet rs = ScheduleQuery.retrieveExercises(conn, schedule)){
//            if (rs.next()) {
//                Exercise exercise = new Exercise(
//                        rs.getInt(ID),
//                        rs.getString(NAME),
//                        rs.getString(DESCRIPTION),
//                        rs.getInt(NUMBERSERIES),
//                        rs.getInt(NUMBERREPS),
//                        RestTime.convertIntToRestTime(rs.getInt(RESTTIME))
//                );
//                exercises.add(exercise);
//            }schedule.setExercises(exercises);
//        } catch (SQLException e) {
//            handleException(e);
//        }
//    }

    @Override
    public void searchSchedules(List<Schedule> schedules, String search, Customer user) {
        try (Connection conn = ConnectionSQL.getConnection()) {
            ResultSet rs = ScheduleQuery.searchSchedules(conn, search, user);
            if (rs.next()) {
                Schedule schedule = new Schedule(
                        rs.getInt(ID),
                        rs.getString(NAME),
                        new Customer(new Credentials(user.getCredentials().getMail(), Role.CLIENT)),
                        new Trainer(new Credentials(rs.getString(TRAINER), Role.TRAINER))
                );
                schedules.add(schedule);
            }
        } catch (SQLException | NoResultException e) {
            throw new DAOException("Errore nella ricerca della scheda", e);
        }
    }


    @Override
    public void deleteSchedule(Schedule schedule) {
        try (Connection conn = ConnectionSQL.getConnection()) {
            ScheduleQuery.deleteSchedule(conn, schedule.getCustomer().getCredentials().getMail(), schedule.getTrainer().getCredentials().getMail(), schedule.getName());
        } catch (SQLException | DbOperationException e) {
            handleException(e);
        }
    }


    private void handleException(Exception e) {
        System.out.println(String.format("%s", e.getMessage()));
    }

}
