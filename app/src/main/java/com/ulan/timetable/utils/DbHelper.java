package com.ulan.timetable.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ulan.timetable.model.Appointment;
import com.ulan.timetable.model.Contact;
import com.ulan.timetable.model.Event;
import com.ulan.timetable.model.Note;
import com.ulan.timetable.model.Task;
import com.ulan.timetable.model.User;
import com.ulan.timetable.model.Week;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ulan on 07.09.2018.
 */
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

    private static final String TASKS = "tasks";
    private static final String TASKS_ID  = "tid";
    private static final String TASKS_NAME = "name";
    private static final String TASKS_DESCRIPTION = "description";
    private static final String TASKS_TYPE = "type";
    private static final String TASKS_DATE = "duedate";
    private static final String TASKS_COLOR = "color";

    private static final String NOTES = "notes";
    private static final String NOTES_ID = "nid";
    private static final String NOTES_TITLE = "title";
    private static final String NOTES_TEXT = "text";
    private static final String NOTES_COLOR = "color";

    private static final String CONTACTS = "contacts";
    private static final String CONTACTS_ID = "cid";
    private static final String CONTACTS_NAME = "name";
    private static final String CONTACTS_ORGANISATION = "organisation";
    private static final String CONTACTS_PHONE_NUMBER = "phonenumber";
    private static final String CONTACTS_EMAIL = "email";
    private static final String CONTACTS_COLOR = "color";


    private static final String EVENTS = "events";
    private static final String EVENTS_ID = "eid";
    private static final String EVENTS_NAME = "name";
    private static final String EVENTS_HOST = "host";
    private static final String EVENTS_LOACTION = "location";
    private static final String EVENTS_DATE = "date";
    private static final String EVENTS_TIME = "time";
    private static final String EVENTS_COLOR = "color";

    private static final String USERS = "users";
    private static final String USERS_ID = "uid";
    private static final String USERS_NAME = "name";
    private static final String USERSID = "userID";
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
                + WEEK_COLOR + " INTEGER" +  ")";

        String CREATE_TASKS = "CREATE TABLE " + TASKS + "("
                + TASKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TASKS_NAME + " TEXT,"
                + TASKS_DESCRIPTION + " TEXT,"
                + TASKS_TYPE + " TEXT,"
                + TASKS_DATE + " TEXT,"
                + TASKS_COLOR + " INTEGER" + ")";

        String CREATE_NOTES = "CREATE TABLE " + NOTES + "("
                + NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NOTES_TITLE + " TEXT,"
                + NOTES_TEXT + " TEXT,"
                + NOTES_COLOR + " INTEGER" + ")";

        String CREATE_CONTACTS = "CREATE TABLE " + CONTACTS + "("
                + CONTACTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CONTACTS_NAME + " TEXT,"
                + CONTACTS_ORGANISATION + " TEXT,"
                + CONTACTS_PHONE_NUMBER + " TEXT,"
                + CONTACTS_EMAIL + " TEXT,"
                + CONTACTS_COLOR + " INTEGER" + ")";


         String CREATE_EVENTS = "CREATE TABLE " + EVENTS + "("
                 + EVENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                 + EVENTS_NAME + " TEXT,"
                 + EVENTS_HOST + " TEXT,"
                 + EVENTS_LOACTION + " TEXT,"
                 + EVENTS_DATE + " TEXT,"
                 + EVENTS_TIME + " TEXT,"
                 + EVENTS_COLOR + " INTEGER" + ")";

         String CREATE_USERS = "CREATE TABLE " + USERS + "("
                 + USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                 + USERS_NAME + " TEXT,"
                 + USERSID + " TEXT,"
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
                 + APPOINTMENTS_COLOR + " INTEGER" + ")";


        db.execSQL(CREATE_TIMETABLE);
        db.execSQL(CREATE_TASKS);
        db.execSQL(CREATE_NOTES);
        db.execSQL(CREATE_CONTACTS);
        db.execSQL(CREATE_EVENTS);
        db.execSQL(CREATE_USERS);
        db.execSQL(CREATE_APPOINTMENTS);
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
        db.update(TIMETABLE, contentValues, WEEK_ID + " = " + week.getTid(), null);
        db.close();
    }

    public ArrayList<Week> getWeek(String fragment){
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Week> weeklist = new ArrayList<>();
        Week week;
        Cursor cursor = db.rawQuery("SELECT * FROM ( SELECT * FROM "+TIMETABLE+" ORDER BY " + WEEK_FROM_TIME + " ) WHERE "+ WEEK_FRAGMENT +" LIKE '"+fragment+"%'",null);
        while (cursor.moveToNext()){
            week = new Week();
            week.setTid(cursor.getInt(cursor.getColumnIndex(WEEK_ID)));
            week.setSubject(cursor.getString(cursor.getColumnIndex(WEEK_SUBJECT)));
            week.setTeacher(cursor.getString(cursor.getColumnIndex(WEEK_TEACHER)));
            week.setRoom(cursor.getString(cursor.getColumnIndex(WEEK_ROOM)));
            week.setFromTime(cursor.getString(cursor.getColumnIndex(WEEK_FROM_TIME)));
            week.setToTime(cursor.getString(cursor.getColumnIndex(WEEK_TO_TIME)));
            week.setColor(cursor.getInt(cursor.getColumnIndex(WEEK_COLOR)));
            weeklist.add(week);
        }
        return  weeklist;
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
        db.update(TASKS, contentValues, TASKS_ID + " = " + task.getId(), null);
        db.close();
    }

    public void deleteTaskById(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TASKS,TASKS_ID + " = ? ", new String[]{String.valueOf(task.getId())});
        db.close();
    }


    public ArrayList<Task> getTask() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Task> tasklist = new ArrayList<>();
        Task task;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TASKS + " ORDER BY datetime(" + TASKS_DATE + ") ASC",null);
        while (cursor.moveToNext()){
            task = new Task();
            task.setId(cursor.getInt(cursor.getColumnIndex(TASKS_ID)));
            task.setName(cursor.getString(cursor.getColumnIndex(TASKS_NAME)));
            task.setDescription(cursor.getString(cursor.getColumnIndex(TASKS_DESCRIPTION)));
            task.setType(cursor.getString(cursor.getColumnIndex(TASKS_TYPE)));
            task.setDuedate(cursor.getString(cursor.getColumnIndex(TASKS_DATE)));
            task.setColor(cursor.getInt(cursor.getColumnIndex(TASKS_COLOR)));
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
        db.insert(NOTES, null, contentValues);
        db.close();
    }

    public void updateNote(Note note)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TITLE, note.getTitle());
        contentValues.put(NOTES_TEXT, note.getText());
        contentValues.put(NOTES_COLOR, note.getColor());
        db.update(NOTES, contentValues, NOTES_ID + " = " + note.getNid(), null);
        db.close();
    }

    public void deleteNoteById(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTES, NOTES_ID + " =? ", new String[] {String.valueOf(note.getNid())});
        db.close();
    }

    public ArrayList<Note> getNote() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Note> notelist = new ArrayList<>();
        Note note;
        Cursor cursor = db.rawQuery("SELECT * FROM " + NOTES, null);
        while (cursor.moveToNext()) {
            note = new Note();
            note.setNid(cursor.getInt(cursor.getColumnIndex(NOTES_ID)));
            note.setTitle(cursor.getString(cursor.getColumnIndex(NOTES_TITLE)));
            note.setText(cursor.getString(cursor.getColumnIndex(NOTES_TEXT)));
            note.setColor(cursor.getInt(cursor.getColumnIndex(NOTES_COLOR)));
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
        db.update(CONTACTS, contentValues, CONTACTS_ID + " = " + contact.getCid(), null);
        db.close();
    }

    public void deleteContactById(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACTS, CONTACTS_ID + " =? ", new String[] {String.valueOf(contact.getCid())});
        db.close();
    }

    public ArrayList<Contact> getContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Contact> contactlist = new ArrayList<>();
        Contact contact;
        Cursor cursor = db.rawQuery("SELECT * FROM " + CONTACTS, null);
        while (cursor.moveToNext()) {
            contact = new Contact();
            contact.setCid(cursor.getInt(cursor.getColumnIndex(CONTACTS_ID)));
            contact.setName(cursor.getString(cursor.getColumnIndex(CONTACTS_NAME)));
            contact.setOrganisation(cursor.getString(cursor.getColumnIndex(CONTACTS_ORGANISATION)));
            contact.setPhonenumber(cursor.getString(cursor.getColumnIndex(CONTACTS_PHONE_NUMBER)));
            contact.setEmail(cursor.getString(cursor.getColumnIndex(CONTACTS_EMAIL)));
            contact.setColor(cursor.getInt(cursor.getColumnIndex(CONTACTS_COLOR)));
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
        db.update(EVENTS, contentValues, EVENTS_ID + " = " + event.getEid(), null);
        db.close();
    }

    public void deleteEventById(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EVENTS, EVENTS_ID + " =? ", new String[] {String.valueOf(event.getEid())});
        db.close();
    }

    public ArrayList<Event> getEvent() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Event> eventslist = new ArrayList<>();
        Event event;
        Cursor cursor = db.rawQuery("SELECT * FROM " + EVENTS, null);
        while (cursor.moveToNext()) {
            event = new Event();
            event.setEid(cursor.getInt(cursor.getColumnIndex(EVENTS_ID)));
            event.setName(cursor.getString(cursor.getColumnIndex(EVENTS_NAME)));
            event.setHost(cursor.getString(cursor.getColumnIndex(EVENTS_HOST)));
            event.setLocation(cursor.getString(cursor.getColumnIndex(EVENTS_LOACTION)));
            event.setDate(cursor.getString(cursor.getColumnIndex(EVENTS_DATE)));
            event.setTime(cursor.getString(cursor.getColumnIndex(EVENTS_TIME)));
            event.setColor(cursor.getInt(cursor.getColumnIndex(EVENTS_COLOR)));
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
        db.update(APPOINTMENTS, contentValues, APPOINTMENTS_ID + " = " + appointment.getAid(), null);
        db.close();
    }

    public void deleteAppointmentById(Appointment appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(APPOINTMENTS, APPOINTMENTS_ID + " =? ", new String[] {String.valueOf(appointment.getAid())});
        db.close();
    }

    public ArrayList<Appointment> getAppointment() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Appointment> appointmentslist = new ArrayList<>();
        Appointment appointment;
        Cursor cursor = db.rawQuery("SELECT * FROM " + APPOINTMENTS, null);
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
            appointmentslist.add(appointment);
        }
        cursor.close();
        db.close();
        return appointmentslist;
    }

    /**
     * Methods for login activity
     **/
    public void insertUsers(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_NAME, user.getName());
        contentValues.put(USERSID, user.getUserID());
        contentValues.put(USERS_ORGANISATION, user.getOrganisation());
        contentValues.put(USERS_PASSWORD, user.getPassword());
        db.insert(USERS, null, contentValues);
        db.close();
    }

    public void updateUsers(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_NAME, user.getName());
        contentValues.put(USERSID, user.getUserID());
        contentValues.put(USERS_ORGANISATION, user.getOrganisation());
        contentValues.put(USERS_PASSWORD, user.getPassword());
        db.update(USERS, contentValues, USERS_ID + " = " + user.getUid(), null);
        db.close();
    }

    public void deleteUserById(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USERS, USERS_ID + " =? ", new String[] {String.valueOf(user.getUid())});
        db.close();
    }

    public ArrayList<User> getUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<User> usersList = new ArrayList<>();
        User user;
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS, null);
        while (cursor.moveToNext()) {
            user = new User();
            user.setUid(cursor.getInt(cursor.getColumnIndex(USERS_ID)));
            user.setName(cursor.getString(cursor.getColumnIndex(USERS_NAME)));
            user.setUserID(cursor.getString(cursor.getColumnIndex(USERSID)));
            user.setOrganisation(cursor.getString(cursor.getColumnIndex(USERS_ORGANISATION)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(USERS_PASSWORD)));
            usersList.add(user);
        }
        cursor.close();
        db.close();
        return usersList;
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

    public boolean addUser(String name, String userID, String organisation, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(USERS_NAME, name);
        contentValues.put(USERSID, userID);
        contentValues.put(USERS_ORGANISATION, organisation);
        contentValues.put(USERS_PASSWORD, password);
        long result=db.insert(USERS,null,contentValues);
        if (result==-1){
            return false;
        } else {
            return true;
        }

    }

    public String getLoginCount(String userID, String password)
    {
        SQLiteDatabase sql = this.getReadableDatabase();
        String query= "SELECT count(*) FROM " + USERS + " where userID = '"+ userID  +"' and password = '" + password + "'";
        Cursor cursor =sql.rawQuery(query,null);
        cursor.moveToFirst();
        String count = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0)));
        return count;

    }

    public String getUserName (String userID) {
        SQLiteDatabase sql = this.getReadableDatabase();
        String query= "SELECT name FROM " + USERS + " where userID = '"+ userID + "'";
        Cursor cursor =sql.rawQuery(query,null);
        cursor.moveToFirst();
        String count = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0)));
        return count;
    }



}
