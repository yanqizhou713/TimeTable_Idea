package com.ulan.timetable.model;

public class Result {
    private String subject, semester, score, uid;
    private int rid;

    public Result() {
    }
    public Result(String subject, String semester, String score, String uid) {
        this.subject = subject;
        this.semester = semester;
        this.score = score;
        this.uid = uid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
}
