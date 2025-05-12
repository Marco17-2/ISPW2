package org.example.project3.model;

import java.time.Period;

public class Subscription {
    private int id;
    private Period type;
    private String name;
    private Float price;
    private Integer disconut;

    public Subscription(int id, Period type, String name, Float price, Integer disconut) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.disconut = disconut;
    }

    public Subscription(int id, Period type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = null;
        this.disconut = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getDisconut() {
        return disconut;
    }

    public void setDisconut(Integer disconut) {
        this.disconut = disconut;
    }

    public Period getType() {
        return type;
    }

    public void setType(Period type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
