package com.ulan.timetable.model;

/**
 * Created by Idea 06/10/2020
 */
public class User {
    private String uid, name, organisation, password;

    public User() {}

    public User(String uid, String name, String organisation, String password) {
        this.uid = uid;
        this.name = name;
        this.organisation = organisation;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
