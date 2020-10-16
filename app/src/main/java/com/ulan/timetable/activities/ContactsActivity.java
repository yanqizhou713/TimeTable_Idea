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
import com.ulan.timetable.adapters.ContactsAdapter;
import com.ulan.timetable.model.Contact;
import com.ulan.timetable.utils.AlertDialogsHelper;
import com.ulan.timetable.utils.DbHelper;

import java.util.ArrayList;


public class ContactsActivity extends AppCompatActivity {

    private Context context = this;
    private ListView listView;
    private DbHelper db;
    private ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        initAll();
    }

    private void initAll() {
        setupAdapter();
        setupListViewMultiSelect();
        setupCustomDialog();
    }

    private void setupAdapter() {
        db = new DbHelper(context);
        listView = findViewById(R.id.contactlist);
        String uid = TempActivity.textView1.getText().toString();
        adapter = new ContactsAdapter(ContactsActivity.this, listView, R.layout.listview_contacts_adapter, db.getContact(uid));
        listView.setAdapter(adapter);
    }

    private void setupListViewMultiSelect() {
        final CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorContacts);
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
                        ArrayList<Contact> removelist = new ArrayList<>();
                        SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                        for(int i = 0; i < checkedItems.size(); i++) {
                            int key = checkedItems.keyAt(i);
                            if (checkedItems.get(key)) {
                                db.deleteContactById(adapter.getItem(key));
                                removelist.add(adapter.getContactList().get(key));
                            }
                        }
                        adapter.getContactList().removeAll(removelist);
                        db.updateContact(adapter.getContact());
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
        final View alertLayout = getLayoutInflater().inflate(R.layout.dialog_add_contact, null);
        AlertDialogsHelper.getAddContactDialog(ContactsActivity.this, alertLayout, adapter);
    }
}
