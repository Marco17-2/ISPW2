package org.example.project3.model;

import java.time.LocalDate;


public class Reservation {

    Customer customer;
    Course course;
    LocalDate dateTime;
    String hour;


    public void Reservation(Customer customer, Course course, LocalDate dateTime, String hour) {
        this.customer = customer;
        this.course = course;
        this.dateTime = dateTime;
        this.hour = hour;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDate(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDate getDate() {
        return dateTime;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
