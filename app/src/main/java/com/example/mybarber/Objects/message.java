package com.example.mybarber.Objects;

public class message {
    private String name;
    private String date;
    private String hour;
    private String action;
    private int id;

    public message(){}

    public message(String name, String date, String hour, String action, int id)
    {
        this.name = name;
        this.date = date;
        this.hour = hour;
        this.action = action;
        this.id = id;
    }

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

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + " " + action +  " at " + date + ", " + hour;
    }
}
