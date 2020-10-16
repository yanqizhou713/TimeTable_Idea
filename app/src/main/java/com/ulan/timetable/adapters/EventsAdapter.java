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
import com.ulan.timetable.model.Event;
import com.ulan.timetable.utils.AlertDialogsHelper;
import com.ulan.timetable.utils.DbHelper;

import java.util.ArrayList;
import java.util.Objects;

public class EventsAdapter extends ArrayAdapter<Event> {

    private Activity mActivity;
    private int mResource;
    private ArrayList<Event> eventlist;
    private Event event;
    private ListView mListView;

    private static class ViewHolder {
        TextView name;
        TextView host;
        TextView location;
        TextView date;
        TextView time;
        CardView cardView;
        ImageView popup;
    }

    public EventsAdapter(Activity activity, ListView listView, int resource, ArrayList<Event> objects) {
        super(activity, resource, objects);
        mActivity = activity;
        mListView = listView;
        mResource = resource;
        eventlist = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        String name = Objects.requireNonNull(getItem(position)).getName();
        String host = Objects.requireNonNull(getItem(position)).getHost();
        String location = Objects.requireNonNull(getItem(position)).getLocation();
        String date = Objects.requireNonNull(getItem(position)).getDate();
        String time = Objects.requireNonNull(getItem(position)).getTime();
        String uid = Objects.requireNonNull(getItem(position)).getUid();
        int color = Objects.requireNonNull(getItem(position)).getColor();

        event = new Event(name, host, date, time, location, color, uid);
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.eventevents);
            holder.host = convertView.findViewById(R.id.hostevents);
            holder.location = convertView.findViewById(R.id.locationevents);
            holder.date = convertView.findViewById(R.id.dateevents);
            holder.time = convertView.findViewById(R.id.timeevents);
            holder.cardView = convertView.findViewById(R.id.events_cardview);
            holder.popup = convertView.findViewById(R.id.popupbtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(event.getName());
        holder.host.setText(event.getHost());
        holder.location.setText(event.getLocation());
        holder.date.setText(event.getDate());
        holder.time.setText(event.getTime());
        holder.cardView.setCardBackgroundColor(event.getColor());
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
                                db.deleteEventById(getItem(position));
                                db.updateEvent(getItem(position));
                                eventlist.remove(position);
                                notifyDataSetChanged();
                                return true;

                            case R.id.edit_popup:
                                final View alertLayout = mActivity.getLayoutInflater().inflate(R.layout.dialog_add_event, null);
                                AlertDialogsHelper.getEditEventDialog(mActivity, alertLayout, eventlist, mListView, position);
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

    public ArrayList<Event> getEventList() {
        return eventlist;
    }

    public Event getEvent() {
        return event;
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
