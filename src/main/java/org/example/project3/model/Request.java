package org.example.project3.model;

import java.time.LocalDateTime;

public class Request {

    private Customer customer;
    private Course course;
    private LocalDateTime datetime;

    public Request(Customer customer, Course course, LocalDateTime dateTime){

        this.customer = customer;
        this.course = course;
        this.datetime = dateTime;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }
}
