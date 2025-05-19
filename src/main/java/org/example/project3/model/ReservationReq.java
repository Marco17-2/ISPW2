package org.example.project3.model;

import java.time.LocalDateTime;

public class ReservationReq {

    Customer customer;
    Course course;
    LocalDateTime dateTime;


    public void ReservationReq(Customer customer, Course course, LocalDateTime dateTime) {
        this.customer = customer;
        this.course = course;
        this.dateTime = dateTime;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDatetime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
