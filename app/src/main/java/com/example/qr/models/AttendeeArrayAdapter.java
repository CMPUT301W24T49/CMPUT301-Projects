// Adapted from Cole Haraga's EventArrayAdapter.java and lab 5 code

package com.example.qr.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qr.R;

import java.util.ArrayList;

public class AttendeeArrayAdapter extends ArrayAdapter<String> {

    private ArrayList<String> attendeeDataList;
    private Context context;

    public AttendeeArrayAdapter(Context context, ArrayList<String> attendees) {
        super(context, 0, attendees);
        this.context = context;
        this.attendeeDataList = attendees;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_attendee_list_info, parent, false);
        }

        String attendee = attendeeDataList.get(position);
        TextView attendeeName = view.findViewById(R.id.attendee_list_info);
        attendeeName.setText(attendee);

        return view;
    }

}
