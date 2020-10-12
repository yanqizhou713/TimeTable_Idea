package com.ulan.timetable.model;

public class Appointment {
    private String topic, teacher, room, date, time, duration;
    private int aid, color;

    public Appointment() {
    }
    public Appointment(String topic, String teacher, String room, String date, String time, String duration, int color) {
        this.topic = topic;
        this.teacher = teacher;
        this.room = room;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.color = color;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
