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
import com.ulan.timetable.model.Result;
import com.ulan.timetable.utils.AlertDialogsHelper;
import com.ulan.timetable.utils.DbHelper;

import java.util.ArrayList;
import java.util.Objects;

public class ResultsAdapter extends ArrayAdapter<Result> {

    private Activity mActivity;
    private int mResource;
    private ArrayList<Result> resultlist;
    private Result result;
    private ListView mListView;

    private static class ViewHolder {
        TextView subject;
        TextView semester;
        TextView score;
        CardView cardView;
    }

    public ResultsAdapter(Activity activity, ListView listView, int resource, ArrayList<Result> objects) {
        super(activity, resource, objects);
        mActivity = activity;
        mListView = listView;
        mResource = resource;
        resultlist = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        String subject = Objects.requireNonNull(getItem(position)).getSubject();
        String semester = Objects.requireNonNull(getItem(position)).getSemester();
        String score = Objects.requireNonNull(getItem(position)).getScore();
        String uid = Objects.requireNonNull(getItem(position)).getUid();

        result = new Result(subject, semester, score, uid);
        final ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.subject = convertView.findViewById(R.id.subjectresult);
            holder.semester = convertView.findViewById(R.id.semesterresult);
            holder.score = convertView.findViewById(R.id.scoreresult);
            holder.cardView = convertView.findViewById(R.id.result_cardview);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.subject.setText(result.getSubject());
        holder.semester.setText(result.getSemester());
        holder.score.setText(result.getScore());

        return convertView;
    }

}