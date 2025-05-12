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
    private CustomerBean customerBean;
    private LocalDateTime dateTime;
    private CourseBean courseBean;

    public RequestBean(long ID, ScheduleBean scheduleBean, ExerciseBean exerciseBean, String reason, CustomerBean customer, LocalDateTime dateTime, CourseBean courseBean) {
        this.ID = ID;
        this.scheduleBean = scheduleBean;
        this.exerciseBean = exerciseBean;
        this.reason = reason;
        this.customerBean = customerBean;
        this.dateTime = dateTime;
        this.courseBean = courseBean;
    }

    public RequestBean(long ID) {
        this.ID = ID;
        this.scheduleBean = null;
        this.exerciseBean = null;
        this.reason = "";
        this.customerBean = null;
        this.dateTime = null;
        this.courseBean = null;
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

    public void setID(long ID) {
        this.ID = ID;
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

    public void setCustomer(CustomerBean customerBean) {
        this.customerBean = customerBean;
    }

    public CustomerBean getCustomer() {
        return customerBean;
    }

    public void setCourse(CourseBean courseBean) {
        this.courseBean = courseBean;
    }

    public CourseBean getCourse() {
        return courseBean;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
