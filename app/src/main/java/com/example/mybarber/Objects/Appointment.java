package com.example.mybarber.Objects;

import java.sql.Time;
import java.util.Date;

public class Appointment {

    //variables
    String id;
    String name;
    String date;
    String time;
    String haircut;
    String phone;

    //constructors
    public Appointment() {
    }

    public Appointment(String name, String date, String time,String haircut, String phone) {
        this.id = date + ", " + time;
        this.name = name;
        this.date = date;
        this.time = time;
        this.haircut = haircut;
        this.phone = phone;
    }

    //getters & setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHaircut() {
        return haircut;
    }

    public void setHaircut(String haircut) {
        this.haircut = haircut;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return date + ", " + time;
    }
}
