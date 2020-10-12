package com.ulan.timetable.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.ulan.timetable.R;
import com.ulan.timetable.adapters.EventsAdapter;
import com.ulan.timetable.model.Event;
import com.ulan.timetable.utils.AlertDialogsHelper;
import com.ulan.timetable.utils.DbHelper;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {

    private Context context = this;
    private ListView listView;
    private EventsAdapter adapter;
    private DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        initAll();
    }

    private void initAll() {
        setupAdapter();
        setupListViewMultiSelect();
        setupCustomDialog();
    }

    private void setupAdapter() {
        db = new DbHelper(context);
        listView = findViewById(R.id.eventslist);
        adapter = new EventsAdapter(EventsActivity.this, listView, R.layout.listview_events_adapter, db.getEvent());
        listView.setAdapter(adapter);
    }

    private void setupListViewMultiSelect() {
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                final int checkedCount = listView.getCheckedItemCount();
                mode.setTitle(checkedCount + " " + getResources().getString(R.string.selected));
                if(checkedCount == 0) mode.finish();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.toolbar_action_mode, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        ArrayList<Event> removelist = new ArrayList<>();
                        SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                        for (int i = 0; i < checkedItems.size(); i++) {
                            int key = checkedItems.keyAt(i);
                            if (checkedItems.get(key)) {
                                db.deleteEventById(adapter.getItem(key));
                                removelist.add(adapter.getEventList().get(key));
                            }
                        }
                        adapter.getEventList().removeAll(removelist);
                        db.updateEvent(adapter.getEvent());
                        adapter.notifyDataSetChanged();
                        mode.finish();
                        return true;

                    default:
                        return false;
                }
            }
            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }
        });
    }

    private void setupCustomDialog() {
        final View alertLayout = getLayoutInflater().inflate(R.layout.dialog_add_event, null);
        AlertDialogsHelper.getAddEventDialog(EventsActivity.this, alertLayout, adapter);
    }
}
