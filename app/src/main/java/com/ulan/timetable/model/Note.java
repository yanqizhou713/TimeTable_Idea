package com.ulan.timetable.model;


import java.io.Serializable;

public class Note implements Serializable {
    private String title, text = "", uid;
    private int nid, color;

    public Note() {}

    public Note(String title, String text, int color, String uid) {
        this.title = title;
        this.text = text;
        this.color = color;
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
