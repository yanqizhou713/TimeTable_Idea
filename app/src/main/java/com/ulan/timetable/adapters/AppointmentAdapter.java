package com.ulan.timetable.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ulan.timetable.R;
import com.ulan.timetable.model.Appointment;
import com.ulan.timetable.utils.AlertDialogsHelper;
import com.ulan.timetable.utils.DbHelper;

import java.util.ArrayList;
import java.util.Objects;

public class AppointmentAdapter extends ArrayAdapter<Appointment> {

    private Activity mActivity;
    private int mResource;
    private ArrayList<Appointment> appointmentlist;
    private Appointment appointment;
    private ListView mListView;

    private static class ViewHolder {
        TextView topic;
        TextView teacher;
        TextView room;
        TextView date;
        TextView time;
        TextView duration;
        CardView cardView;
        ImageView popup;
    }

    public AppointmentAdapter(Activity activity, ListView listView, int resource, ArrayList<Appointment> objects) {
        super(activity, resource, objects);
        mActivity = activity;
        mListView = listView;
        mResource = resource;
        appointmentlist = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        String topic = Objects.requireNonNull(getItem(position)).getTopic();
        String teacher = Objects.requireNonNull(getItem(position)).getTeacher();
        String room = Objects.requireNonNull(getItem(position)).getRoom();
        String date = Objects.requireNonNull(getItem(position)).getDate();
        String time = Objects.requireNonNull(getItem(position)).getTime();
        String duration = Objects.requireNonNull(getItem(position)).getDuration();

        int color = Objects.requireNonNull(getItem(position)).getColor();

        appointment = new Appointment(topic, teacher, room,  date, time, duration, color);
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.topic = convertView.findViewById(R.id.topicappointments);
            holder.teacher = convertView.findViewById(R.id.teacherappointment);
            holder.room = convertView.findViewById(R.id.roomappointment);
            holder.date = convertView.findViewById(R.id.dateappointments);
            holder.time = convertView.findViewById(R.id.timeappointments);
            holder.duration = convertView.findViewById(R.id.durationappointments);
            holder.cardView = convertView.findViewById(R.id.appointments_cardview);
            holder.popup = convertView.findViewById(R.id.popupbtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.topic.setText(appointment.getTopic());
        holder.teacher.setText(appointment.getTeacher());
        holder.room.setText(appointment.getRoom());
        holder.date.setText(appointment.getDate());
        holder.time.setText(appointment.getTime());
        holder.duration.setText(appointment.getDuration());
        holder.cardView.setCardBackgroundColor(appointment.getColor());
        holder.popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(mActivity, holder.popup);
                final DbHelper db = new DbHelper(mActivity);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete_popup:
                                db.deleteAppointmentById(getItem(position));
                                db.updateAppointment(getItem(position));
                                appointmentlist.remove(position);
                                notifyDataSetChanged();
                                return true;

                            case R.id.edit_popup:
                                final View alertLayout = mActivity.getLayoutInflater().inflate(R.layout.dialog_add_appointment, null);
                                AlertDialogsHelper.getEditAppointmentDialog(mActivity, alertLayout, appointmentlist, mListView, position);
                                notifyDataSetChanged();
                                return true;
                            default:
                                return onMenuItemClick(item);
                        }
                    }
                });
                popup.show();
            }
        });

        hidePopUpMenu(holder);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public ArrayList<Appointment> getAppointmentList() {
        return appointmentlist;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    private void hidePopUpMenu(ViewHolder holder) {
        SparseBooleanArray checkedItems = mListView.getCheckedItemPositions();
        if (checkedItems.size() > 0) {
            for (int i = 0; i < checkedItems.size(); i++) {
                int key = checkedItems.keyAt(i);
                if (checkedItems.get(key)) {
                    holder.popup.setVisibility(View.INVISIBLE);
                }
            }
        } else {
            holder.popup.setVisibility(View.VISIBLE);
        }
    }
}
