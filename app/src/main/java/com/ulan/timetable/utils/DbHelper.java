package com.ulan.timetable.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import com.ulan.timetable.model.Appointment;
import com.ulan.timetable.model.Contact;
import com.ulan.timetable.model.Event;
import com.ulan.timetable.model.Note;
import com.ulan.timetable.model.Result;
import com.ulan.timetable.model.Task;
import com.ulan.timetable.model.User;
import com.ulan.timetable.model.Week;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 6;
    private static final String DB_NAME = "timetabledb";
    private static final String TIMETABLE = "timetable";
    private static final String WEEK_ID = "id";
    private static final String WEEK_SUBJECT = "subject";
    private static final String WEEK_FRAGMENT = "fragment";
    private static final String WEEK_TEACHER = "teacher";
    private static final String WEEK_ROOM = "room";
    private static final String WEEK_FROM_TIME = "fromtime";
    private static final String WEEK_TO_TIME = "totime";
    private static final String WEEK_COLOR = "color";
    private static final String WEEK_USERID= "uid";

    private static final String TASKS = "tasks";
    private static final String TASKS_ID  = "tid";
    private static final String TASKS_NAME = "name";
    private static final String TASKS_DESCRIPTION = "description";
    private static final String TASKS_TYPE = "type";
    private static final String TASKS_DATE = "duedate";
    private static final String TASKS_COLOR = "color";
    private static final String TASKS_USERID = "uid";

    private static final String NOTES = "notes";
    private static final String NOTES_ID = "nid";
    private static final String NOTES_TITLE = "title";
    private static final String NOTES_TEXT = "text";
    private static final String NOTES_COLOR = "color";
    private static final String NOTES_USERID = "uid";

    private static final String CONTACTS = "contacts";
    private static final String CONTACTS_ID = "cid";
    private static final String CONTACTS_NAME = "name";
    private static final String CONTACTS_ORGANISATION = "organisation";
    private static final String CONTACTS_PHONE_NUMBER = "phonenumber";
    private static final String CONTACTS_EMAIL = "email";
    private static final String CONTACTS_COLOR = "color";
    private static final String CONTACTS_USERID = "uid";


    private static final String EVENTS = "events";
    private static final String EVENTS_ID = "eid";
    private static final String EVENTS_NAME = "name";
    private static final String EVENTS_HOST = "host";
    private static final String EVENTS_LOACTION = "location";
    private static final String EVENTS_DATE = "date";
    private static final String EVENTS_TIME = "time";
    private static final String EVENTS_COLOR = "color";
    private static final String EVENTS_USERID = "uid";

    private static final String USERS = "users";
    private static final String USERS_ID = "uid";
    private static final String USERS_NAME = "name";
    private static final String USERS_ORGANISATION = "organisation";
    private static final String USERS_PASSWORD = "password";

    private static final String APPOINTMENTS = "appointments";
    private static final String APPOINTMENTS_ID = "aid";
    private static final String APPOINTMENTS_TOPIC = "topic";
    private static final String APPOINTMENTS_TEACHER = "teacher";
    private static final String APPOINTMENTS_ROOM = "room";
    private static final String APPOINTMENTS_DATE = "date";
    private static final String APPOINTMENTS_TIME = "time";
    private static final String APPOINTMENTS_DURATION = "duration";
    private static final String APPOINTMENTS_COLOR = "color";
    private static final String APPOINTMENTS_USERID = "uid";

    private static final String RESULTS = "results";
    private static final String RESULTS_ID = "rid";
    private static final String RESULTS_SUBJECT = "subject";
    private static final String RESULTS_SEMESTER = "semester";
    private static final String RESULTS_SCORE = "score";
    private static final String RESULTS_USERID = "uid";

    public DbHelper(Context context){
        super(context , DB_NAME, null, DB_VERSION);

    }

     public void onCreate(SQLiteDatabase db) {
         String CREATE_TIMETABLE = "CREATE TABLE " + TIMETABLE + "("
                 + WEEK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                 + WEEK_SUBJECT + " TEXT,"
                 + WEEK_FRAGMENT + " TEXT,"
                 + WEEK_TEACHER + " TEXT,"
                 + WEEK_ROOM + " TEXT,"
                 + WEEK_FROM_TIME + " TEXT,"
                 + WEEK_TO_TIME + " TEXT,"
                 + WEEK_COLOR + " INTEGER,"
                 + WEEK_USERID + " TEXT,"
                 + "CONSTRAINT fk_uid FOREIGN KEY(uid) REFERENCES user(uid))";

        String CREATE_TASKS = "CREATE TABLE " + TASKS + "("
                + TASKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TASKS_NAME + " TEXT,"
                + TASKS_DESCRIPTION + " TEXT,"
                + TASKS_TYPE + " TEXT,"
                + TASKS_DATE + " TEXT,"
                + TASKS_COLOR + " INTEGER,"
                + TASKS_USERID + " TEXT,"
                + "CONSTRAINT fk_uid4 FOREIGN KEY(uid) REFERENCES user(uid))";

        String CREATE_NOTES = "CREATE TABLE " + NOTES + "("
                + NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NOTES_TITLE + " TEXT,"
                + NOTES_TEXT + " TEXT,"
                + NOTES_COLOR + " INTEGER,"
                + NOTES_USERID + " TEXT,"
                + "CONSTRAINT fk_uid5 FOREIGN KEY(uid) REFERENCES user(uid))";

        String CREATE_CONTACTS = "CREATE TABLE " + CONTACTS + "("
                + CONTACTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CONTACTS_NAME + " TEXT,"
                + CONTACTS_ORGANISATION + " TEXT,"
                + CONTACTS_PHONE_NUMBER + " TEXT,"
                + CONTACTS_EMAIL + " TEXT,"
                + CONTACTS_COLOR + " INTEGER,"
                + CONTACTS_USERID + " TEXT,"
                + "CONSTRAINT fk_uid2 FOREIGN KEY(uid) REFERENCES user(uid))";


         String CREATE_EVENTS = "CREATE TABLE " + EVENTS + "("
                 + EVENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                 + EVENTS_NAME + " TEXT,"
                 + EVENTS_HOST + " TEXT,"
                 + EVENTS_LOACTION + " TEXT,"
                 + EVENTS_DATE + " TEXT,"
                 + EVENTS_TIME + " TEXT,"
                 + EVENTS_COLOR + " INTEGER, "
                 + EVENTS_USERID + " TEXT,"
                 + "CONSTRAINT fk_uid3 FOREIGN KEY(uid) REFERENCES user(uid))";

         String CREATE_USERS = "CREATE TABLE " + USERS + "("
                 + USERS_ID + " Text PRIMARY KEY,"
                 + USERS_NAME + " TEXT,"
                 + USERS_ORGANISATION + " TEXT,"
                 + USERS_PASSWORD + " TEXT" + ")";

         String CREATE_APPOINTMENTS = "CREATE TABLE " + APPOINTMENTS + "("
                 + APPOINTMENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                 + APPOINTMENTS_TOPIC + " TEXT,"
                 + APPOINTMENTS_TEACHER + " TEXT,"
                 + APPOINTMENTS_ROOM + " TEXT,"
                 + APPOINTMENTS_DATE + " TEXT,"
                 + APPOINTMENTS_TIME + " TEXT,"
                 + APPOINTMENTS_DURATION + " TEXT,"
                 + APPOINTMENTS_COLOR + " INTEGER,"
                 + APPOINTMENTS_USERID + " TEXT,"
                 + "CONSTRAINT fk_uid1 FOREIGN KEY(uid) REFERENCES user(uid))";

         String CREATE_RESULTS = "CREATE TABLE " + RESULTS + "("
                 + RESULTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                 + RESULTS_SUBJECT + " TEXT,"
                 + RESULTS_SEMESTER + " TEXT,"
                 + RESULTS_SCORE + " TEXT,"
                 + RESULTS_USERID + " TEXT" + ")";

         String INSERT_RESULT = "INSERT INTO " + RESULTS + "(" + RESULTS_SUBJECT + ", " + RESULTS_SEMESTER + ", " + RESULTS_SCORE + ", " + RESULTS_USERID + ") VALUES('Java', 'Semester 2, 2019', 'CC', 's3342986');";
         String INSERT_RESULT1 = "INSERT INTO " + RESULTS + "(" + RESULTS_SUBJECT + ", " + RESULTS_SEMESTER + ", " + RESULTS_SCORE + ", " + RESULTS_USERID + ") VALUES('SQL', 'Semester 2, 2019', 'CHD', 's3342986');";
         String INSERT_RESULT2 = "INSERT INTO " + RESULTS + "(" + RESULTS_SUBJECT + ", " + RESULTS_SEMESTER + ", " + RESULTS_SCORE + ", " + RESULTS_USERID + ") VALUES('Database', 'Semester 1, 2020', 'CD', 's3342986');";
         String INSERT_RESULT3 = "INSERT INTO " + RESULTS + "(" + RESULTS_SUBJECT + ", " + RESULTS_SEMESTER + ", " + RESULTS_SCORE + ", " + RESULTS_USERID + ") VALUES('Website', 'Semester 2, 2019', 'CC', 's3796824');";
         String INSERT_RESULT4 = "INSERT INTO " + RESULTS + "(" + RESULTS_SUBJECT + ", " + RESULTS_SEMESTER + ", " + RESULTS_SCORE + ", " + RESULTS_USERID + ") VALUES('Networking', 'Semester 2, 2019', 'CHD', 's3796824');";
         String INSERT_RESULT5 = "INSERT INTO " + RESULTS + "(" + RESULTS_SUBJECT + ", " + RESULTS_SEMESTER + ", " + RESULTS_SCORE + ", " + RESULTS_USERID + ") VALUES('Worksafe', 'Semester 1, 2020', 'CD', 's3796824');";
         String INSERT_RESULT6 = "INSERT INTO " + RESULTS + "(" + RESULTS_SUBJECT + ", " + RESULTS_SEMESTER + ", " + RESULTS_SCORE + ", " + RESULTS_USERID + ") VALUES('Data Object', 'Semester 1, 2019', 'CHD', 's1');";
         String INSERT_RESULT7 = "INSERT INTO " + RESULTS + "(" + RESULTS_SUBJECT + ", " + RESULTS_SEMESTER + ", " + RESULTS_SCORE + ", " + RESULTS_USERID + ") VALUES('PHP', 'Semester 2, 2019', 'CC', 's1');";
         String INSERT_RESULT8 = "INSERT INTO " + RESULTS + "(" + RESULTS_SUBJECT + ", " + RESULTS_SEMESTER + ", " + RESULTS_SCORE + ", " + RESULTS_USERID + ") VALUES('CMS', 'Semester 1, 2020', 'CD', 's1');";


        db.execSQL(CREATE_TIMETABLE);
        db.execSQL(CREATE_TASKS);
        db.execSQL(CREATE_NOTES);
        db.execSQL(CREATE_CONTACTS);
        db.execSQL(CREATE_EVENTS);
        db.execSQL(CREATE_USERS);
        db.execSQL(CREATE_APPOINTMENTS);
        db.execSQL(CREATE_RESULTS);
        db.execSQL(INSERT_RESULT);
        db.execSQL(INSERT_RESULT1);
        db.execSQL(INSERT_RESULT2);
        db.execSQL(INSERT_RESULT3);
        db.execSQL(INSERT_RESULT4);
        db.execSQL(INSERT_RESULT5);
        db.execSQL(INSERT_RESULT6);
        db.execSQL(INSERT_RESULT7);
        db.execSQL(INSERT_RESULT8);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL("DROP TABLE IF EXISTS " + TIMETABLE);

            case 2:
                db.execSQL("DROP TABLE IF EXISTS " + TASKS);

            case 3:
                db.execSQL("DROP TABLE IF EXISTS " + NOTES);

            case 4:
                db.execSQL("DROP TABLE IF EXISTS " + CONTACTS);

            case 5:
                db.execSQL("DROP TABLE IF EXISTS " + EVENTS);
                break;

            case 6:
                db.execSQL("DROP TABLE IF EXISTS " + USERS);
                break;

            case 7:
                db.execSQL("DROP TABLE IF EXISTS " + APPOINTMENTS);
                break;

            case 8:
                db.execSQL("DROP TABLE IF EXISTS " + RESULTS);
                break;

        }
        onCreate(db);
    }

    /**
     * Methods for Week fragments
     **/
    public void insertWeek(Week week){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WEEK_SUBJECT, week.getSubject());
        contentValues.put(WEEK_FRAGMENT, week.getFragment());
        contentValues.put(WEEK_TEACHER, week.getTeacher());
        contentValues.put(WEEK_ROOM, week.getRoom());
        contentValues.put(WEEK_FROM_TIME, week.getFromTime());
        contentValues.put(WEEK_TO_TIME, week.getToTime());
        contentValues.put(WEEK_COLOR, week.getColor());
        contentValues.put(WEEK_USERID, week.getUid());
        db.insert(TIMETABLE,null, contentValues);
        db.update(TIMETABLE, contentValues, WEEK_FRAGMENT, null);
        db.close();
    }

    public void deleteWeekById(Week week) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TIMETABLE, WEEK_ID + " = ? ", new String[]{String.valueOf(week.getTid())});
        db.close();
    }


    public void updateWeek(Week week) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WEEK_SUBJECT, week.getSubject());
        contentValues.put(WEEK_TEACHER, week.getTeacher());
        contentValues.put(WEEK_ROOM, week.getRoom());
        contentValues.put(WEEK_FROM_TIME,week.getFromTime());
        contentValues.put(WEEK_TO_TIME, week.getToTime());
        contentValues.put(WEEK_COLOR, week.getColor());
        contentValues.put(WEEK_USERID, week.getUid());
        db.update(TIMETABLE, contentValues, WEEK_ID + " = " + week.getTid(), null);
        db.close();
    }

    public ArrayList<Week> getWeek(String fragment, String userID){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Week> weeklist = new ArrayList<>();
        Week week;
        String uerID[] = {userID};
        Cursor cursor = db.rawQuery("SELECT * FROM ( SELECT * FROM "+TIMETABLE+" ORDER BY " + WEEK_FROM_TIME + " ) WHERE "+ WEEK_FRAGMENT +" LIKE '"+fragment+"%' AND uid = ?",uerID);
        while (cursor.moveToNext()){
            week = new Week();
            week.setTid(cursor.getInt(cursor.getColumnIndex(WEEK_ID)));
            week.setSubject(cursor.getString(cursor.getColumnIndex(WEEK_SUBJECT)));
            week.setTeacher(cursor.getString(cursor.getColumnIndex(WEEK_TEACHER)));
            week.setRoom(cursor.getString(cursor.getColumnIndex(WEEK_ROOM)));
            week.setFromTime(cursor.getString(cursor.getColumnIndex(WEEK_FROM_TIME)));
            week.setToTime(cursor.getString(cursor.getColumnIndex(WEEK_TO_TIME)));
            week.setColor(cursor.getInt(cursor.getColumnIndex(WEEK_COLOR)));
            week.setUid(cursor.getString(cursor.getColumnIndex(WEEK_USERID)));
            weeklist.add(week);
        }
        return  weeklist;
    }

    public String getUid(String uid){
        SQLiteDatabase db = this.getWritableDatabase();
        String userID = "";
        Cursor cursor = db.rawQuery("SELECT uid FROM users WHERE uid = '" + uid + "'", null);
        if (cursor.moveToNext()){
            if(uid.equals(cursor.getString(cursor.getColumnIndex(USERS_ID)))) {
                userID = uid;
            }
        }
        return userID;
    }




    /**
     * Methods for tasks activity
     **/
    public void insertTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASKS_NAME, task.getName());
        contentValues.put(TASKS_DESCRIPTION, task.getDescription());
        contentValues.put(TASKS_TYPE, task.getType());
        contentValues.put(TASKS_DATE, task.getDuedate());
        contentValues.put(TASKS_COLOR, task.getColor());
        contentValues.put(TASKS_USERID, task.getUid());
        db.insert(TASKS,null, contentValues);
        db.close();
    }

    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASKS_NAME, task.getName());
        contentValues.put(TASKS_DESCRIPTION, task.getDescription());
        contentValues.put(TASKS_TYPE, task.getType());
        contentValues.put(TASKS_DATE, task.getDuedate());
        contentValues.put(TASKS_COLOR, task.getColor());
        contentValues.put(TASKS_USERID, task.getUid());
        db.update(TASKS, contentValues, TASKS_ID + " = " + task.getId(), null);
        db.close();
    }

    public void deleteTaskById(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TASKS,TASKS_ID + " = ? ", new String[]{String.valueOf(task.getId())});
        db.close();
    }


    public ArrayList<Task> getTask(String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Task> tasklist = new ArrayList<>();
        Task task;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TASKS + " WHERE uid = '" + uid + "' ORDER BY datetime(" + TASKS_DATE + ") ASC",null);
        while (cursor.moveToNext()){
            task = new Task();
            task.setId(cursor.getInt(cursor.getColumnIndex(TASKS_ID)));
            task.setName(cursor.getString(cursor.getColumnIndex(TASKS_NAME)));
            task.setDescription(cursor.getString(cursor.getColumnIndex(TASKS_DESCRIPTION)));
            task.setType(cursor.getString(cursor.getColumnIndex(TASKS_TYPE)));
            task.setDuedate(cursor.getString(cursor.getColumnIndex(TASKS_DATE)));
            task.setColor(cursor.getInt(cursor.getColumnIndex(TASKS_COLOR)));
            task.setUid(cursor.getString(cursor.getColumnIndex(TASKS_USERID)));
            tasklist.add(task);
        }
        cursor.close();
        db.close();
        return tasklist;
    }

    /**
     * Methods for Notes activity
     **/
    public void insertNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TITLE, note.getTitle());
        contentValues.put(NOTES_TEXT, note.getText());
        contentValues.put(NOTES_COLOR, note.getColor());
        contentValues.put(NOTES_USERID, note.getUid());
        db.insert(NOTES, null, contentValues);
        db.close();
    }

    public void updateNote(Note note)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TITLE, note.getTitle());
        contentValues.put(NOTES_TEXT, note.getText());
        contentValues.put(NOTES_COLOR, note.getColor());
        contentValues.put(NOTES_USERID, note.getUid());
        db.update(NOTES, contentValues, NOTES_ID + " = " + note.getNid(), null);
        db.close();
    }

    public void deleteNoteById(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTES, NOTES_ID + " =? ", new String[] {String.valueOf(note.getNid())});
        db.close();
    }

    public ArrayList<Note> getNote(String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Note> notelist = new ArrayList<>();
        Note note;
        Cursor cursor = db.rawQuery("SELECT * FROM " + NOTES + " WHERE uid = '" + uid + "'", null);
        while (cursor.moveToNext()) {
            note = new Note();
            note.setNid(cursor.getInt(cursor.getColumnIndex(NOTES_ID)));
            note.setTitle(cursor.getString(cursor.getColumnIndex(NOTES_TITLE)));
            note.setText(cursor.getString(cursor.getColumnIndex(NOTES_TEXT)));
            note.setColor(cursor.getInt(cursor.getColumnIndex(NOTES_COLOR)));
            note.setUid(cursor.getString(cursor.getColumnIndex(NOTES_USERID)));
            notelist.add(note);
        }
        cursor.close();
        db.close();
        return notelist;
    }

    /**
     * Methods for Contacts activity
     **/
    public void insertContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_NAME, contact.getName());
        contentValues.put(CONTACTS_ORGANISATION, contact.getOrganisation());
        contentValues.put(CONTACTS_PHONE_NUMBER, contact.getPhonenumber());
        contentValues.put(CONTACTS_EMAIL, contact.getEmail());
        contentValues.put(CONTACTS_COLOR, contact.getColor());
        contentValues.put(CONTACTS_USERID, contact.getUid());
        db.insert(CONTACTS, null, contentValues);
        db.close();
    }

    public void updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_NAME, contact.getName());
        contentValues.put(CONTACTS_ORGANISATION, contact.getOrganisation());
        contentValues.put(CONTACTS_PHONE_NUMBER, contact.getPhonenumber());
        contentValues.put(CONTACTS_EMAIL, contact.getEmail());
        contentValues.put(CONTACTS_COLOR, contact.getColor());
        contentValues.put(CONTACTS_USERID, contact.getUid());
        db.update(CONTACTS, contentValues, CONTACTS_ID + " = " + contact.getCid(), null);
        db.close();
    }

    public void deleteContactById(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACTS, CONTACTS_ID + " =? ", new String[] {String.valueOf(contact.getCid())});
        db.close();
    }

    public ArrayList<Contact> getContact(String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Contact> contactlist = new ArrayList<>();
        Contact contact;
        Cursor cursor = db.rawQuery("SELECT * FROM " + CONTACTS + " WHERE uid = '" + uid + "'", null);
        while (cursor.moveToNext()) {
            contact = new Contact();
            contact.setCid(cursor.getInt(cursor.getColumnIndex(CONTACTS_ID)));
            contact.setName(cursor.getString(cursor.getColumnIndex(CONTACTS_NAME)));
            contact.setOrganisation(cursor.getString(cursor.getColumnIndex(CONTACTS_ORGANISATION)));
            contact.setPhonenumber(cursor.getString(cursor.getColumnIndex(CONTACTS_PHONE_NUMBER)));
            contact.setEmail(cursor.getString(cursor.getColumnIndex(CONTACTS_EMAIL)));
            contact.setColor(cursor.getInt(cursor.getColumnIndex(CONTACTS_COLOR)));
            contact.setUid(cursor.getString(cursor.getColumnIndex(CONTACTS_USERID)));
            contactlist.add(contact);
        }
        cursor.close();
        db.close();
        return contactlist;
    }


    /**
     * Methods for Event activity
     **/
    public void insertEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENTS_NAME, event.getName());
        contentValues.put(EVENTS_HOST, event.getHost());
        contentValues.put(EVENTS_LOACTION, event.getLocation());
        contentValues.put(EVENTS_DATE, event.getDate());
        contentValues.put(EVENTS_TIME, event.getTime());
        contentValues.put(EVENTS_COLOR, event.getColor());
        contentValues.put(EVENTS_USERID, event.getUid());
        db.insert(EVENTS, null, contentValues);
        db.close();
    }

    public void updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENTS_NAME, event.getName());
        contentValues.put(EVENTS_HOST, event.getHost());
        contentValues.put(EVENTS_LOACTION, event.getLocation());
        contentValues.put(EVENTS_DATE, event.getDate());
        contentValues.put(EVENTS_TIME, event.getTime());
        contentValues.put(EVENTS_COLOR, event.getColor());
        contentValues.put(EVENTS_USERID, event.getUid());
        db.update(EVENTS, contentValues, EVENTS_ID + " = " + event.getEid(), null);
        db.close();
    }

    public void deleteEventById(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EVENTS, EVENTS_ID + " =? ", new String[] {String.valueOf(event.getEid())});
        db.close();
    }

    public ArrayList<Event> getEvent(String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Event> eventslist = new ArrayList<>();
        Event event;
        Cursor cursor = db.rawQuery("SELECT * FROM " + EVENTS + " WHERE uid = '" + uid + "'", null);
        while (cursor.moveToNext()) {
            event = new Event();
            event.setEid(cursor.getInt(cursor.getColumnIndex(EVENTS_ID)));
            event.setName(cursor.getString(cursor.getColumnIndex(EVENTS_NAME)));
            event.setHost(cursor.getString(cursor.getColumnIndex(EVENTS_HOST)));
            event.setLocation(cursor.getString(cursor.getColumnIndex(EVENTS_LOACTION)));
            event.setDate(cursor.getString(cursor.getColumnIndex(EVENTS_DATE)));
            event.setTime(cursor.getString(cursor.getColumnIndex(EVENTS_TIME)));
            event.setColor(cursor.getInt(cursor.getColumnIndex(EVENTS_COLOR)));
            event.setUid(cursor.getString(cursor.getColumnIndex(EVENTS_USERID)));
            eventslist.add(event);
        }
        cursor.close();
        db.close();
        return eventslist;
    }

    /**
     * Methods for Appointment activity
     **/
    public void insertAppointment(Appointment appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(APPOINTMENTS_TOPIC, appointment.getTopic());
        contentValues.put(APPOINTMENTS_TEACHER, appointment.getTeacher());
        contentValues.put(APPOINTMENTS_ROOM, appointment.getRoom());
        contentValues.put(APPOINTMENTS_DATE, appointment.getDate());
        contentValues.put(APPOINTMENTS_TIME, appointment.getTime());
        contentValues.put(APPOINTMENTS_DURATION, appointment.getDuration());
        contentValues.put(APPOINTMENTS_COLOR, appointment.getColor());
        contentValues.put(APPOINTMENTS_USERID, appointment.getUid());
        db.insert(APPOINTMENTS, null, contentValues);
        db.close();
    }

    public void updateAppointment(Appointment appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(APPOINTMENTS_TOPIC, appointment.getTopic());
        contentValues.put(APPOINTMENTS_TEACHER, appointment.getTeacher());
        contentValues.put(APPOINTMENTS_ROOM, appointment.getRoom());
        contentValues.put(APPOINTMENTS_DATE, appointment.getDate());
        contentValues.put(APPOINTMENTS_TIME, appointment.getTime());
        contentValues.put(APPOINTMENTS_DURATION, appointment.getDuration());
        contentValues.put(APPOINTMENTS_COLOR, appointment.getColor());
        contentValues.put(APPOINTMENTS_USERID, appointment.getUid());
        db.update(APPOINTMENTS, contentValues, APPOINTMENTS_ID + " = " + appointment.getAid(), null);
        db.close();
    }

    public void deleteAppointmentById(Appointment appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(APPOINTMENTS, APPOINTMENTS_ID + " =? ", new String[] {String.valueOf(appointment.getAid())});
        db.close();
    }

    public ArrayList<Appointment> getAppointment(String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Appointment> appointmentslist = new ArrayList<>();
        Appointment appointment;
        Cursor cursor = db.rawQuery("SELECT * FROM " + APPOINTMENTS + " WHERE uid = '" + uid + "'", null);
        while (cursor.moveToNext()) {
            appointment = new Appointment();
            appointment.setAid(cursor.getInt(cursor.getColumnIndex(APPOINTMENTS_ID)));
            appointment.setTopic(cursor.getString(cursor.getColumnIndex(APPOINTMENTS_TOPIC)));
            appointment.setTeacher(cursor.getString(cursor.getColumnIndex(APPOINTMENTS_TEACHER)));
            appointment.setRoom(cursor.getString(cursor.getColumnIndex(APPOINTMENTS_ROOM)));
            appointment.setDate(cursor.getString(cursor.getColumnIndex(APPOINTMENTS_DATE)));
            appointment.setTime(cursor.getString(cursor.getColumnIndex(APPOINTMENTS_TIME)));
            appointment.setDuration(cursor.getString(cursor.getColumnIndex(APPOINTMENTS_DURATION)));
            appointment.setColor(cursor.getInt(cursor.getColumnIndex(APPOINTMENTS_COLOR)));
            appointment.setUid(cursor.getString(cursor.getColumnIndex(APPOINTMENTS_USERID)));
            appointmentslist.add(appointment);
        }
        cursor.close();
        db.close();
        return appointmentslist;
    }

    /**
     * Methods for Result activity
     **/


    public ArrayList<Result> getResult(String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Result> resultslist = new ArrayList<>();
        Result result;
        Cursor cursor = db.rawQuery("SELECT * FROM " + RESULTS + " WHERE uid = '" + uid + "'", null);
        while (cursor.moveToNext()) {
            result = new Result();
            result.setSubject(cursor.getString(cursor.getColumnIndex(RESULTS_SUBJECT)));
            result.setSemester(cursor.getString(cursor.getColumnIndex(RESULTS_SEMESTER)));
            result.setScore(cursor.getString(cursor.getColumnIndex(RESULTS_SCORE)));
            result.setUid(cursor.getString(cursor.getColumnIndex(RESULTS_USERID)));
            resultslist.add(result);
        }
        cursor.close();
        db.close();
        return resultslist;
    }


    /**
     * Converts the show table in the database to a JSON string.
     * @param database the database to get the data from.
     * @return a string in JSON format containing all the show data.
     */
    private String getJSONExportString(SQLiteDatabase database) {
        String selectQuery = "SELECT * FROM " + USERS;
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Convert the database to JSON
        JSONArray databaseSet = new JSONArray();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            databaseSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();

        // Convert databaseSet to string and put in file
        return databaseSet.toString();
    }

    public boolean addUser(String uid, String name, String organisation, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(USERS_ID, uid);
        contentValues.put(USERS_NAME, name);
        contentValues.put(USERS_ORGANISATION, organisation);
        contentValues.put(USERS_PASSWORD, password);
        long result=db.insert(USERS,null,contentValues);
        if (result==-1){
            return false;
        } else {
            return true;
        }

    }

    public String getLoginCount(String uid, String password)
    {
        SQLiteDatabase sql = this.getReadableDatabase();
        String query= "SELECT count(*) FROM " + USERS + " where uid = '"+ uid  +"' and password = '" + password + "'";
        Cursor cursor =sql.rawQuery(query,null);
        cursor.moveToFirst();
        String count = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0)));
        cursor.close();
        return count;

    }

    public String getUserName (String uid) {
        SQLiteDatabase sql = this.getReadableDatabase();
        String query= "SELECT name FROM " + USERS + " where uid = '"+ uid + "'";
        Cursor cursor =sql.rawQuery(query,null);
        cursor.moveToFirst();
        String count = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0)));
        cursor.close();
        return count;
    }




}
