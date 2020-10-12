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
import com.ulan.timetable.model.Task;
import com.ulan.timetable.utils.AlertDialogsHelper;
import com.ulan.timetable.utils.DbHelper;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Ulan on 21.09.2018.
 */
public class TasksAdapter extends ArrayAdapter<Task> {

    private Activity mActivity;
    private int mResource;
    private ArrayList<Task> tasklist;
    private Task task;
    private ListView mListView;

    private static class ViewHolder {
        TextView name;
        TextView description;
        TextView type;
        TextView duedate;
        CardView cardView;
        ImageView popup;
    }

    public TasksAdapter(Activity activity, ListView listView, int resource, ArrayList<Task> objects) {
        super(activity, resource, objects);
        mActivity = activity;
        mListView = listView;
        mResource = resource;
        tasklist = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        String name = Objects.requireNonNull(getItem(position)).getName();
        String description = Objects.requireNonNull(getItem(position)).getDescription();
        String type = Objects.requireNonNull(getItem(position)).getType();
        String date = Objects.requireNonNull(getItem(position)).getDuedate();
        int color = Objects.requireNonNull(getItem(position)).getColor();

        task = new Task(name, description, type, date, color);
        final ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.nametask);
            holder.description = convertView.findViewById(R.id.descriptiontask);
            holder.type = convertView.findViewById(R.id.typetask);
            holder.duedate = convertView.findViewById(R.id.datetask);
            holder.cardView = convertView.findViewById(R.id.tasks_cardview);
            holder.popup = convertView.findViewById(R.id.popupbtn);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(task.getName());
        holder.description.setText(task.getDescription());
        holder.type.setText(task.getType());
        holder.duedate.setText(task.getDuedate());
        holder.cardView.setCardBackgroundColor(task.getColor());
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
                                db.deleteTaskById(getItem(position));
                                db.updateTask(getItem(position));
                                tasklist.remove(position);
                                notifyDataSetChanged();
                                return true;

                            case R.id.edit_popup:
                                final View alertLayout = mActivity.getLayoutInflater().inflate(R.layout.dialog_add_task, null);
                                AlertDialogsHelper.getEditTaskDialog(mActivity, alertLayout, tasklist, mListView, position);
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

    public ArrayList<Task> getTaskList() {
        return tasklist;
    }

    public Task getTask() {
        return task;
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

