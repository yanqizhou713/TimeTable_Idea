package com.ulan.timetable.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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
import com.ulan.timetable.adapters.ResultsAdapter;
import com.ulan.timetable.model.Result;
import com.ulan.timetable.utils.AlertDialogsHelper;
import com.ulan.timetable.utils.DbHelper;

import java.util.ArrayList;


public class ResultsActivity extends AppCompatActivity {

    private Context context = this;
    private ListView listView;
    private DbHelper db;
    private ResultsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        initAll();
    }

    private void initAll() {
        setupAdapter();
        setupListViewMultiSelect();
    }

    private void setupAdapter() {
        db = new DbHelper(context);
        listView = findViewById(R.id.resultlist);
        String uid = TempActivity.textView1.getText().toString();
        adapter = new ResultsAdapter(ResultsActivity.this, listView, R.layout.listview_results_adapter, db.getResult(uid));
        listView.setAdapter(adapter);
    }

    private void setupListViewMultiSelect() {
        final CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorResults);
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
                return false;
            }
            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }
        });
    }

}
