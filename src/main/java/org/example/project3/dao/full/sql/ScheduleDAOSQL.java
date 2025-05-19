package org.example.project3.dao.full.sql;

import org.example.project3.dao.ScheduleDAO;
import org.example.project3.exceptions.DAOException;
import org.example.project3.exceptions.DbOperationException;
import org.example.project3.exceptions.NoResultException;
import org.example.project3.model.Customer;
import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;
import org.example.project3.model.Trainer;
import org.example.project3.query.ExerciseQuery;
import org.example.project3.query.ScheduleQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
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
    public void retrieveSchedule(Schedule schedule) {
        try (Connection conn = ConnectionSQL.getConnection();
             ResultSet rs = ScheduleQuery.retrieveSchedules(conn, schedule.getCustomer().getCredentials().getMail())){
            if (rs.next()) {
                schedule.setName(rs.getString(NAME));
                schedule.setCustomer(rs.getObject(CUSTOMER, Customer.class));
                schedule.setTrainer(rs.getObject(TRAINER, Trainer.class));
            }
        } catch (SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void retrieveExercises(Schedule schedule) {
        List<Exercise> exercises = new ArrayList<>();
        try (Connection conn = ConnectionSQL.getConnection();
             ResultSet rs = ScheduleQuery.retrieveExercises(conn, schedule)){
            if (rs.next()) {
                Exercise exercise = new Exercise(
                        rs.getInt(ID),
                        rs.getString(NAME),
                        rs.getString(DESCRIPTION),
                        rs.getInt(NUMBERSERIES),
                        rs.getInt(NUMBERREPS),
                        rs.getObject(RESTTIME, Duration.class)
                );
                exercises.add(exercise);
                schedule.setExercises(exercises);
            }
        } catch (SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void searchSchedules(List<Schedule> schedules, String search){
        try (Connection conn = ConnectionSQL.getConnection()) {
            ResultSet rs = ScheduleQuery.searchSchedules(conn, search);
            if (rs.next()) {
                Schedule schedule = new Schedule(
                        rs.getLong(ID),
                        rs.getString(NAME),
                        rs.getObject(CUSTOMER, Customer.class),
                        rs.getObject(TRAINER, Trainer.class)
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
