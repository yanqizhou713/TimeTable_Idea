package com.ulan.timetable.model;

public class Event {
    private String name, host, time, date, location;
    private int eid, color;

    public Event() {}

    public Event(String name, String host, String time, String date, String location, int color) {
        this.name = name;
        this.host = host;
        this.time = time;
        this.date = date;
        this.location = location;
        this.color = color;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
