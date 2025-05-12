package org.example.project3.beans;

import java.time.LocalDateTime;


public class ReservationBean {

    private CustomerBean customerBean;
    private CourseBean courseBean;
    private LocalDateTime dateTime;

    public void ReservationBean(RequestBean requestBean){
        this.customerBean = requestBean.getCustomer();
        this.courseBean = requestBean.getCourse();
        this.dateTime = requestBean.getDateTime();
    }

    public void setCourse(CourseBean courseBean) {
        this.courseBean = courseBean;
    }

    public void setCustomer(CustomerBean customerBean) {
        this.customerBean = customerBean;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.dateTime = dateTime;
    }

    public CustomerBean getCustomer() {
        return customerBean;
    }

    public CourseBean getCourse() {
        return courseBean;
    }

    public LocalDateTime getDatetime() {
        return dateTime;
    }
}
