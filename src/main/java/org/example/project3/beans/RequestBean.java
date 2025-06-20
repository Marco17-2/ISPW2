package org.example.project3.beans;

import org.example.project3.model.Course;
import org.example.project3.model.Exercise;
import org.example.project3.model.Schedule;

import java.time.LocalDateTime;

public class RequestBean {
    private long ID;
    private ScheduleBean scheduleBean;
    private ExerciseBean exerciseBean;
    private String reason;
    private LocalDateTime dateTime;

    public RequestBean(long ID, ScheduleBean scheduleBean, ExerciseBean exerciseBean, String reason, LocalDateTime dateTime) {
        this.ID = ID;
        this.scheduleBean = scheduleBean;
        this.exerciseBean = exerciseBean;
        this.reason = reason;
        this.dateTime = dateTime;
    }

    public RequestBean(ScheduleBean scheduleBean) {
        this.ID = 0;
        this.scheduleBean = scheduleBean;
        this.exerciseBean = null;
        this.reason = "";
        this.dateTime = null;
    }

    public long getID() {
        return ID;
    }

    public ScheduleBean getScheduleBean() {
        return scheduleBean;
    }

    public ExerciseBean getExerciseBean() {
        return exerciseBean;
    }

    public String getReason() {
        return reason;
    }

    public void setSchedule(ScheduleBean scheduleBean) {
        this.scheduleBean = scheduleBean;
    }

    public void setExercise(ExerciseBean exerciseBean) {
        this.exerciseBean = exerciseBean;
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
