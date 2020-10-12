package com.ulan.timetable.model;

public class Contact {
    private String name, organisation, phonenumber, email;
    private int cid, color;

    public Contact() {
    }
    public Contact(String name, String organisation, String phonenumber, String email, int color) {
        this.name = name;
        this.organisation = organisation;
        this.phonenumber = phonenumber;
        this.email = email;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
