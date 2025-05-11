package org.example.project3.bean;

import model.Course;
import model.Customer;

import java.time.LocalDateTime;


public class ReservationBean {

    private Customer customer;
    private Course course;
    private LocalDateTime dateTime;

    public void Reservation(RequestBean requestBean){
        this.customer = requestBean.getCustomer();
        this.course = requestBean.getCourse();
        this.dateTime = requestBean.getDateTime();
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.dateTime = dateTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDateTime getDatetime() {
        return dateTime;
    }
}
