package com.ulan.timetable.model;

/**
 * Created by Idea 06/10/2020
 */
public class User {
    private String name, userID, organisation, password;
    private int uid;

    public User() {}

    public User(String name, String userID, String organisation, String password) {
        this.name = name;
        this.userID = userID;
        this.organisation = organisation;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
