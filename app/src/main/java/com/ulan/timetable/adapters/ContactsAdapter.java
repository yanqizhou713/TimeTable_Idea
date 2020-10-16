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
import com.ulan.timetable.model.Contact;
import com.ulan.timetable.utils.AlertDialogsHelper;
import com.ulan.timetable.utils.DbHelper;

import java.util.ArrayList;
import java.util.Objects;

public class ContactsAdapter extends ArrayAdapter<Contact> {

    private Activity mActivity;
    private int mResource;
    private ArrayList<Contact> contactlist;
    private Contact contact;
    private ListView mListView;

    private static class ViewHolder {
        TextView name;
        TextView organisation;
        TextView phonenumber;
        TextView email;
        CardView cardView;
        ImageView popup;
    }

    public ContactsAdapter(Activity activity, ListView listView, int resource, ArrayList<Contact> objects) {
        super(activity, resource, objects);
        mActivity = activity;
        mListView = listView;
        mResource = resource;
        contactlist = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        String name = Objects.requireNonNull(getItem(position)).getName();
        String organisation = Objects.requireNonNull(getItem(position)).getOrganisation();
        String phonenumber = Objects.requireNonNull(getItem(position)).getPhonenumber();
        String email = Objects.requireNonNull(getItem(position)).getEmail();
        String uid = Objects.requireNonNull(getItem(position)).getUid();
        int color = Objects.requireNonNull(getItem(position)).getColor();

        contact = new Contact(name, organisation, phonenumber, email, color, uid);
        final ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.name = convertView.findViewById(R.id.namecontact);
            holder.organisation = convertView.findViewById(R.id.organisationcontact);
            holder.phonenumber = convertView.findViewById(R.id.numbercontact);
            holder.email = convertView.findViewById(R.id.emailcontact);
            holder.cardView = convertView.findViewById(R.id.contact_cardview);
            holder.popup = convertView.findViewById(R.id.popupbtn);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(contact.getName());
        holder.organisation.setText(contact.getOrganisation());
        holder.phonenumber.setText(contact.getPhonenumber());
        holder.email.setText(contact.getEmail());
        holder.cardView.setCardBackgroundColor(contact.getColor());
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
                                db.deleteContactById(getItem(position));
                                db.updateContact(getItem(position));
                                contactlist.remove(position);
                                notifyDataSetChanged();
                                return true;

                            case R.id.edit_popup:
                                final View alertLayout = mActivity.getLayoutInflater().inflate(R.layout.dialog_add_contact, null);
                                AlertDialogsHelper.getEditContactDialog(mActivity, alertLayout, contactlist, mListView, position);
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

    public ArrayList<Contact> getContactList() {
        return contactlist;
    }

    public Contact getContact() {
        return contact;
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