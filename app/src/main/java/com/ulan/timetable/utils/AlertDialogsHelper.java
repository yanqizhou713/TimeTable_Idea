package com.ulan.timetable.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ulan.timetable.adapters.AppointmentAdapter;
import com.ulan.timetable.adapters.ContactsAdapter;
import com.ulan.timetable.adapters.EventsAdapter;
import com.ulan.timetable.adapters.FragmentsTabAdapter;
import com.ulan.timetable.adapters.NotesAdapter;
import com.ulan.timetable.adapters.TasksAdapter;
import com.ulan.timetable.adapters.WeekAdapter;
import com.ulan.timetable.model.Appointment;
import com.ulan.timetable.model.Contact;
import com.ulan.timetable.model.Event;
import com.ulan.timetable.model.Note;
import com.ulan.timetable.model.Task;
import com.ulan.timetable.model.Week;
import com.ulan.timetable.R;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Ulan on 22.10.2018.
 */
public class AlertDialogsHelper {

    public static void getEditSubjectDialog(final Activity activity, final View alertLayout, final ArrayList<Week> adapter, final ListView listView, int position) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText subject = alertLayout.findViewById(R.id.subject_dialog);
        editTextHashs.put(R.string.subject, subject);
        final EditText teacher = alertLayout.findViewById(R.id.teacher_dialog);
        editTextHashs.put(R.string.teacher, teacher);
        final EditText room = alertLayout.findViewById(R.id.room_dialog);
        editTextHashs.put(R.string.room, room);
        final TextView from_time = alertLayout.findViewById(R.id.from_time);
        final TextView to_time = alertLayout.findViewById(R.id.to_time);
        final Button select_color = alertLayout.findViewById(R.id.select_color);
        final Week week = adapter.get(position);

        subject.setText(week.getSubject());
        teacher.setText(week.getTeacher());
        room.setText(week.getRoom());
        from_time.setText(week.getFromTime());
        to_time.setText(week.getToTime());
        select_color.setBackgroundColor(week.getColor() != 0 ? week.getColor() : Color.WHITE);

        from_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                from_time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                week.setFromTime(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.setTitle(R.string.choose_time);
                timePickerDialog.show();
            }
        });

        to_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                to_time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                week.setToTime(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, hour, minute, true);
                timePickerDialog.setTitle(R.string.choose_time);
                timePickerDialog.show();
            }
        });

        select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mSelectedColor = ContextCompat.getColor(activity, R.color.white);
                select_color.setBackgroundColor(mSelectedColor);
                int[] mColors = activity.getResources().getIntArray(R.array.default_colors);
                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                        mColors,
                        mSelectedColor,
                        5,
                        ColorPickerDialog.SIZE_SMALL,
                        true
                );

                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        select_color.setBackgroundColor(color);
                    }
                });
                dialog.show(activity.getFragmentManager(), "color_dialog");
            }
        });

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(R.string.edit_subject);
        alert.setCancelable(false);
        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(subject.getText()) || TextUtils.isEmpty(teacher.getText()) || TextUtils.isEmpty(room.getText())) {
                    for (Map.Entry<Integer, EditText> entry : editTextHashs.entrySet()) {
                        if(TextUtils.isEmpty(entry.getValue().getText())) {
                            entry.getValue().setError(activity.getResources().getString(entry.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            entry.getValue().requestFocus();
                        }
                    }
                } else if(!from_time.getText().toString().matches(".*\\d+.*") || !to_time.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.time_error, Snackbar.LENGTH_LONG).show();
                } else {
                    DbHelper db = new DbHelper(activity);
                    WeekAdapter weekAdapter = (WeekAdapter) listView.getAdapter(); // In order to get notifyDataSetChanged() method.
                    ColorDrawable buttonColor = (ColorDrawable) select_color.getBackground();
                    week.setSubject(subject.getText().toString());
                    week.setTeacher(teacher.getText().toString());
                    week.setRoom(room.getText().toString());
                    week.setColor(buttonColor.getColor());
                    db.updateWeek(week);
                    weekAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
    }

    public static void getAddSubjectDialog(final Activity activity, final View alertLayout, final FragmentsTabAdapter adapter, final ViewPager viewPager) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText subject = alertLayout.findViewById(R.id.subject_dialog);
        editTextHashs.put(R.string.subject, subject);
        final EditText teacher = alertLayout.findViewById(R.id.teacher_dialog);
        editTextHashs.put(R.string.teacher, teacher);
        final EditText room = alertLayout.findViewById(R.id.room_dialog);
        editTextHashs.put(R.string.room, room);
        final TextView from_time = alertLayout.findViewById(R.id.from_time);
        final TextView to_time = alertLayout.findViewById(R.id.to_time);
        final Button select_color = alertLayout.findViewById(R.id.select_color);
        final Week week = new Week();

        from_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                from_time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                week.setFromTime(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.setTitle(R.string.choose_time);
                timePickerDialog.show(); }});

        to_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                to_time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                week.setToTime(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, hour, minute, true);
                timePickerDialog.setTitle(R.string.choose_time);
                timePickerDialog.show();
            }
        });

        select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mSelectedColor = ContextCompat.getColor(activity, R.color.white);
                select_color.setBackgroundColor(mSelectedColor);
                int[] mColors = activity.getResources().getIntArray(R.array.default_colors);
                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                        mColors,
                        mSelectedColor,
                        5,
                        ColorPickerDialog.SIZE_SMALL,
                        true
                );

                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        select_color.setBackgroundColor(color);
                    }
                });
                dialog.show(activity.getFragmentManager(), "color_dialog");
            }
        });

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(R.string.add_subject);
        alert.setCancelable(false);
        Button cancel = alertLayout.findViewById(R.id.cancel);
        Button submit = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        FloatingActionButton fab = activity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(subject.getText()) || TextUtils.isEmpty(teacher.getText()) || TextUtils.isEmpty(room.getText())) {
                    for (Map.Entry<Integer, EditText> entry : editTextHashs.entrySet()) {
                        if(TextUtils.isEmpty(entry.getValue().getText())) {
                            entry.getValue().setError(activity.getResources().getString(entry.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            entry.getValue().requestFocus();
                        }
                    }
                } else if(!from_time.getText().toString().matches(".*\\d+.*") || !to_time.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.time_error, Snackbar.LENGTH_LONG).show();
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    Matcher fragment = Pattern.compile("(.*Fragment)").matcher(adapter.getItem(viewPager.getCurrentItem()).toString());
                    ColorDrawable buttonColor = (ColorDrawable) select_color.getBackground();
                    week.setSubject(subject.getText().toString());
                    week.setFragment(fragment.find() ? fragment.group() : null);
                    week.setTeacher(teacher.getText().toString());
                    week.setRoom(room.getText().toString());
                    week.setColor(buttonColor.getColor());
                    dbHelper.insertWeek(week);
                    adapter.notifyDataSetChanged();
                    subject.getText().clear();
                    teacher.getText().clear();
                    room.getText().clear();
                    from_time.setText(R.string.select_time);
                    to_time.setText(R.string.select_time);
                    select_color.setBackgroundColor(Color.WHITE);
                    subject.requestFocus();
                    dialog.dismiss();
                }
            }
        });
    }

    public static void getEditTaskDialog(final Activity activity, final View alertLayout, final ArrayList<Task> adapter, final ListView listView, int listposition) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText name = alertLayout.findViewById(R.id.nametask);
        editTextHashs.put(R.string.name, name);
        final EditText description = alertLayout.findViewById(R.id.descriptiontask);
        editTextHashs.put(R.string.desctiption, description);
        final EditText type = alertLayout.findViewById(R.id.typetask);
        editTextHashs.put(R.string.type, type);
        final TextView date = alertLayout.findViewById(R.id.datetask);
        final Button select_color = alertLayout.findViewById(R.id.select_color);
        final Task task = adapter.get(listposition);

        name.setText(task.getName());
        description.setText(task.getDescription());
        type.setText(task.getType());
        date.setText(task.getDuedate());
        select_color.setBackgroundColor(task.getColor() != 0 ? task.getColor() : Color.WHITE);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mdayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(String.format("%02d-%02d-%02d", year, month+1, dayOfMonth));
                        task.setDuedate(String.format("%02d-%02d-%02d", year, month+1, dayOfMonth));
                    }
                }, mYear, mMonth, mdayofMonth);
                datePickerDialog.setTitle(R.string.choose_date);
                datePickerDialog.show();
            }
        });

        select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mSelectedColor = ContextCompat.getColor(activity, R.color.white);
                select_color.setBackgroundColor(mSelectedColor);
                int[] mColors = activity.getResources().getIntArray(R.array.default_colors);
                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                        mColors,
                        mSelectedColor,
                        5,
                        ColorPickerDialog.SIZE_SMALL,
                        true
                );

                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        select_color.setBackgroundColor(color);
                    }
                });
                dialog.show(activity.getFragmentManager(), "color_dialog");
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(R.string.edit_task);
        alert.setCancelable(false);
        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(description.getText()) || TextUtils.isEmpty(type.getText())) {
                    for (Map.Entry<Integer, EditText> editText : editTextHashs.entrySet()) {
                        if (TextUtils.isEmpty(editText.getValue().getText())) {
                            editText.getValue().setError(activity.getResources().getString(editText.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            editText.getValue().requestFocus();
                        }
                    }
                } else if (!date.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.deadline_snackbar, Snackbar.LENGTH_LONG).show();
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    TasksAdapter tasksAdapter = (TasksAdapter) listView.getAdapter();
                    ColorDrawable buttonColor = (ColorDrawable) select_color.getBackground();
                    task.setName(name.getText().toString());
                    task.setDescription(description.getText().toString());
                    task.setType(type.getText().toString());
                    task.setColor(buttonColor.getColor());
                    dbHelper.updateTask(task);
                    tasksAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
            });
    }

    public static void getAddTaskDialog(final Activity activity, final View alertLayout, final TasksAdapter adapter) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText name = alertLayout.findViewById(R.id.nametask);
        editTextHashs.put(R.string.name, name);
        final EditText description = alertLayout.findViewById(R.id.descriptiontask);
        editTextHashs.put(R.string.desctiption, description);
        final EditText type = alertLayout.findViewById(R.id.typetask);
        editTextHashs.put(R.string.type, type);
        final TextView date = alertLayout.findViewById(R.id.datetask);
        final Button select_color = alertLayout.findViewById(R.id.select_color);
        final Task task = new Task();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mdayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(String.format("%02d-%02d-%02d", year, month+1, dayOfMonth));
                        task.setDuedate(String.format("%02d-%02d-%02d", year, month+1, dayOfMonth));
                    }
                }, mYear, mMonth, mdayofMonth);
                datePickerDialog.setTitle(R.string.choose_date);
                datePickerDialog.show();
            }
        });

        select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mSelectedColor = ContextCompat.getColor(activity, R.color.white);
                select_color.setBackgroundColor(mSelectedColor);
                int[] mColors = activity.getResources().getIntArray(R.array.default_colors);
                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                        mColors,
                        mSelectedColor,
                        5,
                        ColorPickerDialog.SIZE_SMALL,
                        true
                );

                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        select_color.setBackgroundColor(color);
                    }
                });
                dialog.show(activity.getFragmentManager(), "color_dialog");
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(R.string.add_task);
        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        final AlertDialog dialog = alert.create();
        FloatingActionButton fab = activity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(description.getText()) || TextUtils.isEmpty(type.getText())) {
                    for (Map.Entry<Integer, EditText> editText : editTextHashs.entrySet()) {
                        if(TextUtils.isEmpty(editText.getValue().getText())) {
                            editText.getValue().setError(activity.getResources().getString(editText.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            editText.getValue().requestFocus();
                        }
                    }
                } else if(!date.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.deadline_snackbar, Snackbar.LENGTH_LONG).show();
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    ColorDrawable buttonColor = (ColorDrawable) select_color.getBackground();
                    task.setName(name.getText().toString());
                    task.setDescription(description.getText().toString());
                    task.setType(type.getText().toString());
                    task.setColor(buttonColor.getColor());
                    dbHelper.insertTask(task);

                    adapter.clear();
                    adapter.addAll(dbHelper.getTask());
                    adapter.notifyDataSetChanged();

                    name.getText().clear();
                    description.getText().clear();
                    type.getText().clear();
                    date.setText(R.string.select_date);
                    select_color.setBackgroundColor(Color.WHITE);
                    name.requestFocus();
                    dialog.dismiss();
                }
            }
        });
    }

    public static void getEditContactDialog(final Activity activity, final View alertLayout, final ArrayList<Contact> adapter, final ListView listView, int listposition) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText name = alertLayout.findViewById(R.id.name_dialog);
        editTextHashs.put(R.string.name, name);
        final EditText organisation = alertLayout.findViewById(R.id.organisation_dialog);
        editTextHashs.put(R.string.organisation, organisation);
        final EditText phone_number = alertLayout.findViewById(R.id.phonenumber_dialog);
        editTextHashs.put(R.string.phone_number, phone_number);
        final EditText email = alertLayout.findViewById(R.id.email_dialog);
        editTextHashs.put(R.string.email, email);
        final Button select_color = alertLayout.findViewById(R.id.select_color);
        final Contact contact = adapter.get(listposition);

        name.setText(contact.getName());
        organisation.setText(contact.getOrganisation());
        phone_number.setText(contact.getPhonenumber());
        email.setText(contact.getEmail());
        select_color.setBackgroundColor(contact.getColor() != 0 ? contact.getColor() : Color.WHITE);

        select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mSelectedColor = ContextCompat.getColor(activity, R.color.white);
                select_color.setBackgroundColor(mSelectedColor);
                int[] mColors = activity.getResources().getIntArray(R.array.default_colors);
                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                        mColors,
                        mSelectedColor,
                        5,
                        ColorPickerDialog.SIZE_SMALL,
                        true
                );

                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        select_color.setBackgroundColor(color);
                    }
                });
                dialog.show(activity.getFragmentManager(), "color_dialog");
            }
        });

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(R.string.edit_contact);
        alert.setCancelable(false);
        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(organisation.getText()) || TextUtils.isEmpty(phone_number.getText()) || TextUtils.isEmpty(email.getText())) {
                    for (Map.Entry<Integer, EditText> entry : editTextHashs.entrySet()) {
                        if(TextUtils.isEmpty(entry.getValue().getText())) {
                            entry.getValue().setError(activity.getResources().getString(entry.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            entry.getValue().requestFocus();
                        }
                    }
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    ContactsAdapter teachersAdapter = (ContactsAdapter) listView.getAdapter();
                    ColorDrawable buttonColor = (ColorDrawable) select_color.getBackground();
                    contact.setName(name.getText().toString());
                    contact.setOrganisation(organisation.getText().toString());
                    contact.setPhonenumber(phone_number.getText().toString());
                    contact.setEmail(email.getText().toString());
                    contact.setColor(buttonColor.getColor());
                    dbHelper.updateContact(contact);
                    teachersAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
    }

    public static void getAddContactDialog(final Activity activity, final View alertLayout, final ContactsAdapter adapter) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText name = alertLayout.findViewById(R.id.name_dialog);
        editTextHashs.put(R.string.name, name);
        final EditText organisation = alertLayout.findViewById(R.id.organisation_dialog);
        editTextHashs.put(R.string.organisation, organisation);
        final EditText phone_number = alertLayout.findViewById(R.id.phonenumber_dialog);
        editTextHashs.put(R.string.phone_number, phone_number);
        final EditText email = alertLayout.findViewById(R.id.email_dialog);
        editTextHashs.put(R.string.email, email);
        final Button select_color = alertLayout.findViewById(R.id.select_color);
        final Contact contact = new Contact();

        select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mSelectedColor = ContextCompat.getColor(activity, R.color.white);
                select_color.setBackgroundColor(mSelectedColor);
                int[] mColors = activity.getResources().getIntArray(R.array.default_colors);
                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                        mColors,
                        mSelectedColor,
                        5,
                        ColorPickerDialog.SIZE_SMALL,
                        true
                );

                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        select_color.setBackgroundColor(color);
                    }
                });
                dialog.show(activity.getFragmentManager(), "color_dialog");
            }
        });

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(activity.getResources().getString(R.string.add_contact));
        alert.setCancelable(false);
        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        FloatingActionButton fab = activity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(organisation.getText()) || TextUtils.isEmpty(phone_number.getText()) || TextUtils.isEmpty(email.getText())) {
                    for (Map.Entry<Integer, EditText> entry : editTextHashs.entrySet()) {
                        if(TextUtils.isEmpty(entry.getValue().getText())) {
                            entry.getValue().setError(activity.getResources().getString(entry.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            entry.getValue().requestFocus();
                        }
                    }
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    ColorDrawable buttonColor = (ColorDrawable) select_color.getBackground();
                    contact.setName(name.getText().toString());
                    contact.setOrganisation(organisation.getText().toString());
                    contact.setPhonenumber(phone_number.getText().toString());
                    contact.setEmail(email.getText().toString());
                    contact.setColor(buttonColor.getColor());
                    dbHelper.insertContact(contact);

                    adapter.clear();
                    adapter.addAll(dbHelper.getContact());
                    adapter.notifyDataSetChanged();

                    name.getText().clear();
                    organisation.getText().clear();
                    phone_number.getText().clear();
                    email.getText().clear();
                    select_color.setBackgroundColor(Color.WHITE);
                    name.requestFocus();
                    dialog.dismiss();
                }
            }
        });
    }

    public static void getEditNoteDialog(final Activity activity, final View alertLayout, final ArrayList<Note> adapter, final ListView listView, int listposition) {
        final EditText title = alertLayout.findViewById(R.id.titlenote);
        final Button select_color = alertLayout.findViewById(R.id.select_color);
        final Note note = adapter.get(listposition);
        title.setText(note.getTitle());
        select_color.setBackgroundColor(note.getColor() != 0 ? note.getColor() : Color.WHITE);

        select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mSelectedColor = ContextCompat.getColor(activity, R.color.white);
                select_color.setBackgroundColor(mSelectedColor);
                int[] mColors = activity.getResources().getIntArray(R.array.default_colors);
                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                        mColors,
                        mSelectedColor,
                        5,
                        ColorPickerDialog.SIZE_SMALL,
                        true
                );

                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        select_color.setBackgroundColor(color);
                    }
                });
                dialog.show(activity.getFragmentManager(), "color_dialog");
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(R.string.edit_note);
        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        final AlertDialog dialog = alert.create();
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(title.getText())) {
                    title.setError(activity.getResources().getString(R.string.title_error));
                    title.requestFocus();
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    ColorDrawable buttonColor = (ColorDrawable) select_color.getBackground();
                    note.setTitle(title.getText().toString());
                    note.setColor(buttonColor.getColor());
                    dbHelper.updateNote(note);
                    NotesAdapter notesAdapter = (NotesAdapter) listView.getAdapter();
                    notesAdapter.notifyDataSetChanged();

                    dialog.dismiss();
                }
            }
        });
    }

    public static void getAddNoteDialog(final Activity activity, final View alertLayout, final NotesAdapter adapter) {
        final EditText title = alertLayout.findViewById(R.id.titlenote);
        final Button select_color = alertLayout.findViewById(R.id.select_color);
        final Note note = new Note();

        select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mSelectedColor = ContextCompat.getColor(activity, R.color.white);
                select_color.setBackgroundColor(mSelectedColor);
                int[] mColors = activity.getResources().getIntArray(R.array.default_colors);
                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                        mColors,
                        mSelectedColor,
                        5,
                        ColorPickerDialog.SIZE_SMALL,
                        true
                );

                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        select_color.setBackgroundColor(color);
                    }
                });
                dialog.show(activity.getFragmentManager(), "color_dialog");
            }
        });

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(R.string.add_note);
        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        final AlertDialog dialog = alert.create();
        FloatingActionButton fab = activity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(title.getText())) {
                    title.setError(activity.getResources().getString(R.string.title_error));
                    title.requestFocus();
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    ColorDrawable buttonColor = (ColorDrawable) select_color.getBackground();
                    note.setTitle(title.getText().toString());
                    note.setColor(buttonColor.getColor());
                    dbHelper.insertNote(note);

                    adapter.clear();
                    adapter.addAll(dbHelper.getNote());
                    adapter.notifyDataSetChanged();

                    title.getText().clear();
                    select_color.setBackgroundColor(Color.WHITE);
                    dialog.dismiss();
                }
            }
        });
    }


    public static void getEditEventDialog(final Activity activity, final View alertLayout, final ArrayList<Event> adapter, final ListView listView, int listposition) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText events = alertLayout.findViewById(R.id.eventevent_dialog);
        editTextHashs.put(R.string.event, events);
        final EditText host = alertLayout.findViewById(R.id.hostevent_dialog);
        editTextHashs.put(R.string.host, host);
        final EditText location = alertLayout.findViewById(R.id.locationevent_dialog);
        editTextHashs.put(R.string.location, location);
        final TextView date = alertLayout.findViewById(R.id.dateevent_dialog);
        final TextView time = alertLayout.findViewById(R.id.timeevent_dialog);
        final Button select_color = alertLayout.findViewById(R.id.select_color);
        final Event event = adapter.get(listposition);

        events.setText(event.getName());
        host.setText(event.getHost());
        location.setText(event.getLocation());
        date.setText(event.getDate());
        time.setText(event.getTime());
        select_color.setBackgroundColor(event.getColor());

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mdayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(String.format("%02d-%02d-%02d", year, month+1, dayOfMonth));
                        event.setDate(String.format("%02d-%02d-%02d", year, month+1, dayOfMonth));
                    }
                }, mYear, mMonth, mdayofMonth);
                datePickerDialog.setTitle(R.string.choose_date);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                event.setTime(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.setTitle(R.string.choose_time);
                timePickerDialog.show();
            }
        });


        select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mSelectedColor = ContextCompat.getColor(activity, R.color.white);
                select_color.setBackgroundColor(mSelectedColor);
                int[] mColors = activity.getResources().getIntArray(R.array.default_colors);
                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                        mColors,
                        mSelectedColor,
                        5,
                        ColorPickerDialog.SIZE_SMALL,
                        true
                );

                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        select_color.setBackgroundColor(color);
                    }
                });
                dialog.show(activity.getFragmentManager(), "color_dialog");
            }
        });

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(activity.getResources().getString(R.string.add_event));
        alert.setCancelable(false);
        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(events.getText()) || TextUtils.isEmpty(host.getText()) || TextUtils.isEmpty(location.getText())) {
                    for (Map.Entry<Integer, EditText> entry : editTextHashs.entrySet()) {
                        if(TextUtils.isEmpty(entry.getValue().getText())) {
                            entry.getValue().setError(activity.getResources().getString(entry.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            entry.getValue().requestFocus();
                        }
                    }
                } else if (!date.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.date_error, Snackbar.LENGTH_LONG).show();
                } else if (!time.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.time_error, Snackbar.LENGTH_LONG).show();
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    ColorDrawable buttonColor = (ColorDrawable) select_color.getBackground();
                    event.setName(events.getText().toString());
                    event.setHost(host.getText().toString());
                    event.setLocation(location.getText().toString());
                    event.setColor(buttonColor.getColor());

                    dbHelper.updateEvent(event);

                    EventsAdapter eventsAdapter = (EventsAdapter) listView.getAdapter();
                    eventsAdapter.notifyDataSetChanged();

                    dialog.dismiss();
                }
            }
        });
    }

    public static void getAddEventDialog(final Activity activity, final View alertLayout, final EventsAdapter adapter) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText events = alertLayout.findViewById(R.id.eventevent_dialog);
        editTextHashs.put(R.string.event, events);
        final EditText host = alertLayout.findViewById(R.id.hostevent_dialog);
        editTextHashs.put(R.string.host, host);
        final EditText location = alertLayout.findViewById(R.id.locationevent_dialog);
        editTextHashs.put(R.string.location, location);
        final TextView date = alertLayout.findViewById(R.id.dateevent_dialog);
        final TextView time = alertLayout.findViewById(R.id.timeevent_dialog);
        final Button select_color = alertLayout.findViewById(R.id.select_color);
        final Event event = new Event();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mdayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(String.format("%02d-%02d-%02d", year, month+1, dayOfMonth));
                        event.setDate(String.format("%02d-%02d-%02d", year, month+1, dayOfMonth));
                    }
                }, mYear, mMonth, mdayofMonth);
                datePickerDialog.setTitle(R.string.choose_date);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                event.setTime(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.setTitle(R.string.choose_time);
                timePickerDialog.show();
            }
        });


        select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mSelectedColor = ContextCompat.getColor(activity, R.color.white);
                select_color.setBackgroundColor(mSelectedColor);
                int[] mColors = activity.getResources().getIntArray(R.array.default_colors);
                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                        mColors,
                        mSelectedColor,
                        5,
                        ColorPickerDialog.SIZE_SMALL,
                        true
                );

                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        select_color.setBackgroundColor(color);
                    }
                });
                dialog.show(activity.getFragmentManager(), "color_dialog");
            }
        });

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(activity.getResources().getString(R.string.add_event));
        alert.setCancelable(false);
        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        FloatingActionButton fab = activity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(events.getText()) || TextUtils.isEmpty(host.getText()) || TextUtils.isEmpty(location.getText())) {
                    for (Map.Entry<Integer, EditText> entry : editTextHashs.entrySet()) {
                        if(TextUtils.isEmpty(entry.getValue().getText())) {
                            entry.getValue().setError(activity.getResources().getString(entry.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            entry.getValue().requestFocus();
                        }
                    }
                } else if (!date.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.date_error, Snackbar.LENGTH_LONG).show();
                } else if (!time.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.time_error, Snackbar.LENGTH_LONG).show();
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    ColorDrawable buttonColor = (ColorDrawable) select_color.getBackground();
                    event.setName(events.getText().toString());
                    event.setHost(host.getText().toString());
                    event.setLocation(location.getText().toString());
                    event.setColor(buttonColor.getColor());

                    dbHelper.insertEvent(event);

                    adapter.clear();
                    adapter.addAll(dbHelper.getEvent());
                    adapter.notifyDataSetChanged();

                    events.getText().clear();
                    host.getText().clear();
                    location.getText().clear();
                    date.setText(R.string.select_date);
                    time.setText(R.string.select_time);
                    select_color.setBackgroundColor(Color.WHITE);
                    events.requestFocus();
                    dialog.dismiss();
                }
            }
        });
    }


    public static void getEditAppointmentDialog(final Activity activity, final View alertLayout, final ArrayList<Appointment> adapter, final ListView listView, int listposition) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText topic = alertLayout.findViewById(R.id.topicappointment_dialog);
        editTextHashs.put(R.string.topic, topic);
        final EditText teacher = alertLayout.findViewById(R.id.teacherappointment_dialog);
        editTextHashs.put(R.string.teacher, teacher);
        final EditText room = alertLayout.findViewById(R.id.roomappointment_dialog);
        editTextHashs.put(R.string.room, room);
        final TextView date = alertLayout.findViewById(R.id.dateappointment_dialog);
        final TextView time = alertLayout.findViewById(R.id.timeappointment_dialog);
        final EditText duration = alertLayout.findViewById(R.id.durationappointment_dialog);
        editTextHashs.put(R.string.duration, duration);
        final Button select_color = alertLayout.findViewById(R.id.select_color);
        final Appointment appointment = adapter.get(listposition);

        topic.setText(appointment.getTopic());
        teacher.setText(appointment.getTeacher());
        room.setText(appointment.getRoom());
        date.setText(appointment.getDate());
        time.setText(appointment.getTime());
        duration.setText(appointment.getDuration());
        select_color.setBackgroundColor(appointment.getColor());

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mdayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(String.format("%02d-%02d-%02d", year, month+1, dayOfMonth));
                        appointment.setDate(String.format("%02d-%02d-%02d", year, month+1, dayOfMonth));
                    }
                }, mYear, mMonth, mdayofMonth);
                datePickerDialog.setTitle(R.string.choose_date);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                appointment.setTime(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.setTitle(R.string.choose_time);
                timePickerDialog.show();
            }
        });


        select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mSelectedColor = ContextCompat.getColor(activity, R.color.white);
                select_color.setBackgroundColor(mSelectedColor);
                int[] mColors = activity.getResources().getIntArray(R.array.default_colors);
                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                        mColors,
                        mSelectedColor,
                        5,
                        ColorPickerDialog.SIZE_SMALL,
                        true
                );

                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        select_color.setBackgroundColor(color);
                    }
                });
                dialog.show(activity.getFragmentManager(), "color_dialog");
            }
        });

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(activity.getResources().getString(R.string.add_appointment));
        alert.setCancelable(false);
        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(topic.getText()) || TextUtils.isEmpty(teacher.getText()) || TextUtils.isEmpty(room.getText()) || TextUtils.isEmpty(duration.getText())) {
                    for (Map.Entry<Integer, EditText> entry : editTextHashs.entrySet()) {
                        if(TextUtils.isEmpty(entry.getValue().getText())) {
                            entry.getValue().setError(activity.getResources().getString(entry.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            entry.getValue().requestFocus();
                        }
                    }
                } else if (!date.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.date_error, Snackbar.LENGTH_LONG).show();
                } else if (!time.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.time_error, Snackbar.LENGTH_LONG).show();
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    ColorDrawable buttonColor = (ColorDrawable) select_color.getBackground();
                    appointment.setTopic(topic.getText().toString());
                    appointment.setTeacher(teacher.getText().toString());
                    appointment.setRoom(room.getText().toString());
                    appointment.setDuration(duration.getText().toString());
                    appointment.setColor(buttonColor.getColor());

                    dbHelper.updateAppointment(appointment);

                    AppointmentAdapter appointmentAdapter = (AppointmentAdapter) listView.getAdapter();
                    appointmentAdapter.notifyDataSetChanged();

                    dialog.dismiss();
                }
            }
        });
    }

    public static void getAddAppointmentDialog(final Activity activity, final View alertLayout, final AppointmentAdapter adapter) {
        final HashMap<Integer, EditText> editTextHashs = new HashMap<>();
        final EditText topic = alertLayout.findViewById(R.id.topicappointment_dialog);
        editTextHashs.put(R.string.topic, topic);
        final EditText teacher = alertLayout.findViewById(R.id.teacherappointment_dialog);
        editTextHashs.put(R.string.teacher, teacher);
        final EditText room = alertLayout.findViewById(R.id.roomappointment_dialog);
        editTextHashs.put(R.string.room, room);
        final TextView date = alertLayout.findViewById(R.id.dateappointment_dialog);
        final TextView time = alertLayout.findViewById(R.id.timeappointment_dialog);
        final EditText duration = alertLayout.findViewById(R.id.durationappointment_dialog);
        editTextHashs.put(R.string.duration, duration);
        final Button select_color = alertLayout.findViewById(R.id.select_color);
        final Appointment appointment = new Appointment();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mdayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(String.format("%02d-%02d-%02d", year, month+1, dayOfMonth));
                        appointment.setDate(String.format("%02d-%02d-%02d", year, month+1, dayOfMonth));
                    }
                }, mYear, mMonth, mdayofMonth);
                datePickerDialog.setTitle(R.string.choose_date);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                appointment.setTime(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.setTitle(R.string.choose_time);
                timePickerDialog.show();
            }
        });


        select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mSelectedColor = ContextCompat.getColor(activity, R.color.white);
                select_color.setBackgroundColor(mSelectedColor);
                int[] mColors = activity.getResources().getIntArray(R.array.default_colors);
                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                        mColors,
                        mSelectedColor,
                        5,
                        ColorPickerDialog.SIZE_SMALL,
                        true
                );

                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        select_color.setBackgroundColor(color);
                    }
                });
                dialog.show(activity.getFragmentManager(), "color_dialog");
            }
        });

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(activity.getResources().getString(R.string.add_appointment));
        alert.setCancelable(false);
        final Button cancel = alertLayout.findViewById(R.id.cancel);
        final Button save = alertLayout.findViewById(R.id.save);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        FloatingActionButton fab = activity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(topic.getText()) || TextUtils.isEmpty(teacher.getText()) || TextUtils.isEmpty(room.getText()) || TextUtils.isEmpty(duration.getText())) {
                    for (Map.Entry<Integer, EditText> entry : editTextHashs.entrySet()) {
                        if(TextUtils.isEmpty(entry.getValue().getText())) {
                            entry.getValue().setError(activity.getResources().getString(entry.getKey()) + " " + activity.getResources().getString(R.string.field_error));
                            entry.getValue().requestFocus();
                        }
                    }
                } else if (!date.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.date_error, Snackbar.LENGTH_LONG).show();
                } else if (!time.getText().toString().matches(".*\\d+.*")) {
                    Snackbar.make(alertLayout, R.string.time_error, Snackbar.LENGTH_LONG).show();
                } else {
                    DbHelper dbHelper = new DbHelper(activity);
                    ColorDrawable buttonColor = (ColorDrawable) select_color.getBackground();
                    appointment.setTopic(topic.getText().toString());
                    appointment.setTeacher(teacher.getText().toString());
                    appointment.setRoom(room.getText().toString());
                    appointment.setDuration(duration.getText().toString());
                    appointment.setColor(buttonColor.getColor());

                    dbHelper.insertAppointment(appointment);

                    adapter.clear();
                    adapter.addAll(dbHelper.getAppointment());
                    adapter.notifyDataSetChanged();

                    topic.getText().clear();
                    teacher.getText().clear();
                    room.getText().clear();
                    date.setText(R.string.select_date);
                    time.setText(R.string.select_time);
                    duration.getText().clear();
                    select_color.setBackgroundColor(Color.WHITE);
                    topic.requestFocus();
                    dialog.dismiss();
                }
            }
        });
    }
}
